package projeto.integracaoB3.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.integracaoB3.modelo.UsuarioAcesso;
import projeto.integracaoB3.repository.IntegracaoB3Service;
import projeto.seguranca.controle.Seguranca;

@RestController
@RequestMapping(value = "/api/integracao")
public class IntegracaoApi {

    @Autowired
    private IntegracaoB3Service b3Service;
    @Autowired
    private Seguranca seguranca;

    @PostMapping(value = "/acesso")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> criarAcesso(@RequestHeader HttpHeaders sec,@RequestBody UsuarioAcesso acesso) throws Exception {
        try {
            seguranca.validarToken(sec);
            Integer result = b3Service.obterAcesso(acesso);

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            throw new Exception("Erro ao criar acesso", e);
        }
    }

    @PostMapping(value = "/cadastrarPapeisPlanilha")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> cadastrarPapeisPlanilha(@RequestHeader HttpHeaders sec,@RequestBody String planilha) throws Exception {
        try {
            Integer result = b3Service.cadastrarPapeisPlanilha(planilha, seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            throw new Exception("Erro ao Cadastrar Papeis Planilha", e);
        }
    }

    @PostMapping(value = "/cadastrarInvestimentoPlanilha")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> cadastrarInvestimentoPlanilha(@RequestHeader HttpHeaders sec,@RequestBody String planilha) throws Exception {
        try {
            seguranca.validarToken(sec);
            Integer result = b3Service.cadastrarInvestimentoPlanilha(planilha, seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            throw new Exception("Erro ao Cadastrar Investimento Planilha", e);
        }
    }
}
