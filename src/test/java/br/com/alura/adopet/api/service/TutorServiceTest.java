package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;

    @Mock
    private CadastroTutorDto dto;

    @Mock
    private Tutor tutor;

    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Test
    void naoDeveriaCadastrarTutorTelefoneOuEmailJaCadastrado() {
        //ARRANGE + ACT
        given(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(true);

        //ASSERT
        assertThrows(ValidacaoException.class, () -> service.cadastrar(dto));
    }

    @Test
    void deveriaCadastrarTutor() {
        //ARRANGE
        given(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(false);

        //ACT + ASSERT
        assertDoesNotThrow(() -> service.cadastrar(dto));
        then(repository).should().save(new Tutor(dto));
    }

    @Test
    void deveriaAtualizarDadosTutor() {
        //ARRANGE
        given(repository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);

        //ACT
        service.atualizar(atualizacaoTutorDto);

        //ASSERT
        then(tutor).should().atualizarDados(atualizacaoTutorDto);
    }

}