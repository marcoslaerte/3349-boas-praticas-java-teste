package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {
    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validador;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoTutorComAdocaoEmAndamento() {
        //ARRANGE
        given(adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        //ACT + ASSERT
        assertThrows(ValidacaoException.class, () -> validador.validar(dto));
    }

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoTutorSemAdocaoEmAndamento() {
        //ARRANGE
        given(adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        //ACT + ASSERT
        assertDoesNotThrow(() -> validador.validar(dto));
    }
}