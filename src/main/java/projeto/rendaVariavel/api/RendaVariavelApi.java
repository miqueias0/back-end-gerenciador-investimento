package projeto.rendaVariavel.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.rendaFixa.modelo.InvestimentoRendaFixaModelo;
import projeto.rendaVariavel.modelo.InvestimentoRendaVariavelModelo;
import projeto.rendaVariavel.repository.InvestimentoRendaVariavelService;
import projeto.seguranca.controle.Seguranca;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rendaVariavel")
public class RendaVariavelApi {

    @Autowired
    private InvestimentoRendaVariavelService service;
    @Autowired
    private Seguranca seguranca;

    @GetMapping("/obterPorId")
    public ResponseEntity<InvestimentoRendaVariavelModelo> obterPorId(@RequestHeader HttpHeaders sec,
                                                                            @RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorPrazo")
    public ResponseEntity<List<InvestimentoRendaVariavelModelo>> obterListaPorPrazo(@RequestHeader HttpHeaders sec,
                                                                                @RequestParam("prazoInicial") Date prazoInicial,
                                                                                @RequestParam("prazoFinal") Date prazoFinal) {
        try {
            return ResponseEntity.ok(service.findByPrazo(seguranca.validarToken(sec).getIdentificador(),
                    prazoInicial, prazoFinal));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorPapel")
    public ResponseEntity<List<InvestimentoRendaVariavelModelo>> obterListaPorPapel(@RequestHeader HttpHeaders sec,
                                                                            @RequestParam("papel") String papel) {
        try {
            return ResponseEntity.ok(service.findByPapelAndIdUsuario(papel,
                    seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterLista")
    public ResponseEntity<List<InvestimentoRendaVariavelModelo>> obterLista(@RequestHeader HttpHeaders sec,
                                                                        @RequestParam("descricao") String descricao) {
        try {
            return ResponseEntity.ok(service.findByDescricaoAndIdUsuario(descricao,
                    seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorIdUsuario")
    public ResponseEntity<List<InvestimentoRendaVariavelModelo>> obterListaPorIdUsuario(@RequestHeader HttpHeaders sec) {
        try {
            return ResponseEntity.ok(service.findByIdUsuario(seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorListaRendaFixa")
    public ResponseEntity<List<InvestimentoRendaVariavelModelo>> obterListaPorListaRendaFixa(@RequestHeader HttpHeaders sec,
                                                                                         @RequestBody List<String> rendaVariavel) {
        try {
            return ResponseEntity.ok(service.findByRendaVariavelCodigoInAndIdUsuario(rendaVariavel,
                    seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/manter")
    public ResponseEntity<InvestimentoRendaVariavelModelo> manter(@RequestHeader HttpHeaders sec,
                                                                  @RequestBody InvestimentoRendaVariavelModelo variavelModelo) {
        try {
            variavelModelo.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(service.save(variavelModelo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/alterar")
    public ResponseEntity<InvestimentoRendaVariavelModelo> alterar(@RequestHeader HttpHeaders sec,
                                                               @RequestBody InvestimentoRendaVariavelModelo variavelModelo) {
        try {
            variavelModelo.setDescricao(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(service.update(variavelModelo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
