package projeto.integracaoB3.repository;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.classeInvestimento.dao.IRendaVariavel;
import projeto.classeInvestimento.modelo.RendaFixa;
import projeto.classeInvestimento.modelo.RendaVariavel;
import projeto.integracaoB3.modelo.UsuarioAcesso;
import projeto.rendaVariavel.dao.IInvestimentoRendaVariavel;
import projeto.rendaVariavel.modelo.InvestimentoRendaVariavelModelo;
import projeto.usuario.dao.IUsuario;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegracaoB3Service {

//    @Autowired(required = true)
//    private IIntegracaoB3 integracaoB3;
    @Autowired
    private IRendaVariavel variavel;
    @Autowired
    private IInvestimentoRendaVariavel investimentoRendaVariavel;
    @Autowired
    private IUsuario iUsuario;

    public Integer obterAcesso(UsuarioAcesso acesso) throws Exception {
        try {
            HttpsURLConnection https = (HttpsURLConnection) new URL("https://desenv.gefic.com.br/gefic/api/ordemServico/obterTodos").openConnection();

            https.setRequestMethod("POST");
            https.setRequestProperty("Accept", "application/json");
            https.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            https.setDoOutput(true);
            https.setDoInput(true);

            JSONObject acessoJson = new JSONObject();
            acessoJson.put("nome", acesso.getNome());
            acessoJson.put("documento", acesso.getDocumento());
            acessoJson.put("email", acesso.getEmail());

            OutputStream stream = https.getOutputStream();
            stream.write(acessoJson.toJSONString().getBytes(StandardCharsets.UTF_8));
            stream.flush();

            if (https.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Erro ao fazer a requisição: " + https.getResponseCode());
            }

//            Integer codigo = https.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(https.getInputStream()));

            https.disconnect();

            return reader.readLine().equalsIgnoreCase("sucesso") ? 200 : HttpsURLConnection.HTTP_NOT_FOUND;

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public int cadastrarPapeisPlanilha(String planilha, String idUsuario) throws Exception {
        try {
            Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(Base64.getDecoder().decode(planilha)));
            Integer i = 1;
            Sheet sheetAt = workbook.getSheetAt(0);
            while (sheetAt.getRow(i) != null){
                try {
                    RendaVariavel rendaVariavel = new RendaVariavel();
                    Cell cell = sheetAt.getRow(i).getCell(3);
                    rendaVariavel.setDescricao(cell.getStringCellValue().contains("-")?
                            cell.getStringCellValue().split("-")[1].trim(): cell.getStringCellValue().split(" ")[1].trim());
                    rendaVariavel.setCodigo(cell.getStringCellValue().contains("-")?
                            cell.getStringCellValue().split("-")[0].trim(): cell.getStringCellValue().split(" ")[0].trim());
                    rendaVariavel.setIdUsuario(idUsuario);
                    if(!rendaVariavel.getCodigo().toLowerCase().contains("futuro") && !rendaVariavel.getCodigo().toLowerCase().contains("tesouro")){
                        if(variavel.findByCodigoAndIdUsuario(rendaVariavel.getCodigo(), idUsuario) == null){
                            variavel.save(rendaVariavel);
                        }
                    }
                    i++;
                }catch (Exception ex){
                    if(ex.getMessage().toLowerCase().contains("null")){
                        return 1;
                    }
                    throw new Exception(ex.getCause());
                }
            }
            return 1;
        } catch (Exception ex) {
            throw new Exception("Erro ao cadastrar planilha " + ex.getMessage());
        }
    }

    public int cadastrarInvestimentoPlanilha(String planilha, String id) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(Base64.getDecoder().decode(planilha)));
            Integer i = 1;
            Sheet sheetAt = workbook.getSheetAt(0);
            while (sheetAt.getRow(i) != null){
                try {
                    RendaVariavel rendaVariavel = variavel.findByCodigoAndIdUsuario(sheetAt.getRow(i).getCell(3).getStringCellValue().contains("-")? sheetAt.getRow(i).getCell(3).getStringCellValue().split("-")[0].trim(): sheetAt.getRow(i).getCell(3).getStringCellValue().split(" ")[0].trim(), id);
                    if(rendaVariavel != null && (sheetAt.getRow(i).getCell(2).getStringCellValue().toLowerCase().contains("rendimento")
                            || sheetAt.getRow(i).getCell(2).getStringCellValue().toLowerCase().contains("juros")
                            || sheetAt.getRow(i).getCell(2).getStringCellValue().toLowerCase().contains("dividendo"))
                            && sheetAt.getRow(i).getCell(0).getStringCellValue().toLowerCase().contains("credito")
                            && !sheetAt.getRow(i).getCell(2).getStringCellValue().toLowerCase().contains("direito")){
                        InvestimentoRendaVariavelModelo variavelModelo = new InvestimentoRendaVariavelModelo();
                        variavelModelo.setIdUsuario(id);
                        variavelModelo.setQuantidade(BigDecimal.valueOf(sheetAt.getRow(i).getCell(5).getNumericCellValue()));
                        variavelModelo.setPrecoUnitario(BigDecimal.valueOf(sheetAt.getRow(i).getCell(6).getNumericCellValue()));
                        variavelModelo.setValorToral(BigDecimal.valueOf(sheetAt.getRow(i).getCell(7).getNumericCellValue()));
                        variavelModelo.setDataCompra(sdf.parse(sheetAt.getRow(i).getCell(1).getStringCellValue()));
                        variavelModelo.setDividendoMensal(BigDecimal.valueOf(sheetAt.getRow(i).getCell(7).getNumericCellValue()));
                        variavelModelo.setRendaVariavel(rendaVariavel);
                        variavelModelo.setPapel(rendaVariavel.getCodigo());
                        variavelModelo.setDescricao(rendaVariavel.getDescricao());
                        investimentoRendaVariavel.save(variavelModelo);
                    }else if(rendaVariavel != null) {
                        InvestimentoRendaVariavelModelo variavelModelo = new InvestimentoRendaVariavelModelo();
                        variavelModelo.setIdUsuario(id);
                        variavelModelo.setQuantidade(BigDecimal.valueOf(sheetAt.getRow(i).getCell(5).getNumericCellValue()));
                        if(sheetAt.getRow(i).getCell(6).getCellType().toString().toLowerCase().equals("string")
                                || sheetAt.getRow(i).getCell(7).getCellType().toString().toLowerCase().equals("string")
                                || sheetAt.getRow(i).getCell(5).getNumericCellValue() == 0D){
                            i++;
                            continue;
                        }
                        variavelModelo.setPrecoUnitario(BigDecimal.valueOf(sheetAt.getRow(i).getCell(6).getNumericCellValue()));
                        variavelModelo.setValorToral(BigDecimal.valueOf(sheetAt.getRow(i).getCell(7).getNumericCellValue()));
                        variavelModelo.setValorMedio(variavelModelo.getPrecoUnitario());
                        variavelModelo.setDataCompra(sdf.parse(sheetAt.getRow(i).getCell(1).getStringCellValue()));
                        variavelModelo.setRendaVariavel(rendaVariavel);
                        variavelModelo.setPapel(rendaVariavel.getCodigo());
                        variavelModelo.setDescricao(rendaVariavel.getDescricao());
                        if(sheetAt.getRow(i).getCell(0).getStringCellValue().toLowerCase().contains("credito")){
                            variavelModelo.setMovimentacao("C");
                        }else if(sheetAt.getRow(i).getCell(0).getStringCellValue().toLowerCase().contains("debito")){
                            variavelModelo.setMovimentacao("V");
                        }
                        investimentoRendaVariavel.save(variavelModelo);
                    }
                    i++;
                }catch (Exception ex){
                    throw new Exception(ex.getCause());
                }
            }
            return 1;
        } catch (Exception ex) {
            throw new Exception("Erro ao cadastrar planilha " + ex.getMessage());
        }
    }
}
