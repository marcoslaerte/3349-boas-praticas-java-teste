package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository repository;

    @Mock
    private Abrigo abrigo;

    @Mock
    private PetRepository petRepository;

    @Test
    void deveriaChamarListaDeTodosOsAbrigos() {
        //ACT
        service.listar();

        //ASSERT
        then(repository).should().findAll();
    }

    @Test
    void deveriaChamarListaDePetsDoAbrigoAtravesDoNome() {
        //ARRANGE
        String nome = "Miau";
        given(repository.findByNome(nome)).willReturn(Optional.of(abrigo));

        //ACT
        service.listarPetsDoAbrigo(nome);

        //ASSERT
        then(petRepository).should().findByAbrigo(abrigo);
    }

}