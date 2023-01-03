package projeto.rendaFixa.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.rendaFixa.modelo.InvestimentoRendaFixaModelo;
import projeto.rendaFixa.repository.InvestimentoRendaFixaService;
import projeto.rendaVariavel.modelo.InvestimentoRendaVariavelModelo;
import projeto.seguranca.controle.Seguranca;

import java.util.List;

@RestController
@RequestMapping("/api/rendaFixa")
public class RendaFixaApi {

    @Autowired
    private InvestimentoRendaFixaService investimentoRendaFixaService;
    @Autowired
    private Seguranca seguranca;

    @GetMapping("/obterPorId")
    public ResponseEntity<InvestimentoRendaFixaModelo> obterPorId(@RequestHeader HttpHeaders sec,
                                                                      @RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(investimentoRendaFixaService.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorPrazo")
    public ResponseEntity<List<InvestimentoRendaFixaModelo>> obterListaPorPrazo(@RequestHeader HttpHeaders sec,
                                                                        @RequestParam("prazoInicial") String prazoInicial,
                                                                                @RequestParam("prazoFinal") String prazoFinal) {
        try {
            return ResponseEntity.ok(investimentoRendaFixaService.findByPrazo(seguranca.validarToken(sec).getIdentificador(),
                    prazoInicial, prazoFinal));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterLista")
    public ResponseEntity<List<InvestimentoRendaFixaModelo>> obterLista(@RequestHeader HttpHeaders sec,
                                                                        @RequestParam("descricao") String descricao) {
        try {
            return ResponseEntity.ok(investimentoRendaFixaService.findByDescricao(descricao,
                    seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorIdUsuario")
    public ResponseEntity<List<InvestimentoRendaFixaModelo>> obterListaPorIdUsuario(@RequestHeader HttpHeaders sec) {
        try {
            return ResponseEntity.ok(investimentoRendaFixaService.findByIdUsuario(seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorListaPerfil")
    public ResponseEntity<List<InvestimentoRendaFixaModelo>> obterListaPorListaPerfil(@RequestHeader HttpHeaders sec,
                                                                        @RequestBody List<String> perfils) {
        try {
            return ResponseEntity.ok(investimentoRendaFixaService.findByListPerfil(perfils,
                    seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorListaRentabilidade")
    public ResponseEntity<List<InvestimentoRendaFixaModelo>> obterListaPorListaRentabilidade(@RequestHeader HttpHeaders sec,
                                                                        @RequestBody List<String> rentabilidades) {
        try {
            return ResponseEntity.ok(investimentoRendaFixaService.findByListRentabilidade(rentabilidades,
                    seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorEmissor")
    public ResponseEntity<List<InvestimentoRendaFixaModelo>> obterListaPorEmissor(@RequestHeader HttpHeaders sec,
                                                                        @RequestParam("emissor") String emissor) {
        try {
            return ResponseEntity.ok(investimentoRendaFixaService.findByEmissor(emissor,
                    seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterListaPorListaRendaFixa")
    public ResponseEntity<List<InvestimentoRendaFixaModelo>> obterListaPorListaRendaFixa(@RequestHeader HttpHeaders sec,
                                                                                    @RequestBody List<String> rendaFixas) {
        try {
            return ResponseEntity.ok(investimentoRendaFixaService.findByListRendaFixa(rendaFixas,
                    seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/manter")
    public ResponseEntity<InvestimentoRendaFixaModelo> manter(@RequestHeader HttpHeaders sec,
                                                              @RequestBody InvestimentoRendaFixaModelo investimentoRendaFixaModelo) {
        try {
            investimentoRendaFixaModelo.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(investimentoRendaFixaService.save(investimentoRendaFixaModelo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/alterar")
    public ResponseEntity<InvestimentoRendaFixaModelo> alterar(@RequestHeader HttpHeaders sec,
                                                               @RequestBody InvestimentoRendaFixaModelo investimentoRendaFixaModelo) {
        try {
            investimentoRendaFixaModelo.setDescricao(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(investimentoRendaFixaService.update(investimentoRendaFixaModelo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
