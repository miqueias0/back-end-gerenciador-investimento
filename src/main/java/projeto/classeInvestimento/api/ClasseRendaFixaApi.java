package projeto.classeInvestimento.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.seguranca.controle.Seguranca;
import projeto.classeInvestimento.modelo.RendaFixa;
import projeto.classeInvestimento.repository.RendaFixaService;

import java.util.List;

@RestController
@RequestMapping("/api/classeRendaFixa")
public class ClasseRendaFixaApi {

    @Autowired
    private RendaFixaService rendaFixaService;
    @Autowired
    private Seguranca seguranca;

    @GetMapping("/obterLista")
    public ResponseEntity<List<RendaFixa>> obterLista(@RequestHeader HttpHeaders sec){
        try {
            return ResponseEntity.ok(rendaFixaService.findAll(seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterPorCodigo")
    public ResponseEntity<RendaFixa> obterPorCodigo(@RequestHeader HttpHeaders sec, @RequestParam("codigo") String codigo){
        try {
            return ResponseEntity.ok(rendaFixaService.findByCodigo(codigo, seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/manter")
    public ResponseEntity<RendaFixa> manter(@RequestHeader HttpHeaders sec, @RequestBody RendaFixa rendaFixa){
        try {
            rendaFixa.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(rendaFixaService.save(rendaFixa));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/alterar")
    public ResponseEntity<RendaFixa> alterar(@RequestHeader HttpHeaders sec, @RequestBody RendaFixa rendaFixa){
        try {
            rendaFixa.setDescricao(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(rendaFixaService.update(rendaFixa));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
