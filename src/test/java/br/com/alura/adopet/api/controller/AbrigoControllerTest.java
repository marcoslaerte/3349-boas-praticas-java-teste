package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Mock
    private Abrigo abrigo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarAbrigos() throws Exception {
        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeCadastrarAbrigo() throws Exception {
        //ARRANGE
        String json = """
                {
                    "nome": "Abrigo feliz",
                    "telefone": "(94)0000-9090",
                    "email": "email@example.com.br"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaRequisicaoDeCadastrarAbrigo() throws Exception {
        //ARRANGE
        String json = """
                {
                    "nome": "Abrigo feliz",
                    "telefone": "(94)0000-90900",
                    "email": "email@example.com.br"
                }
                """;

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarPetsDoAbrigoPorNome() throws Exception {
        //ARRANGE
        String nome = "Abrigo feliz";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{nome}/pets",nome)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarPetsDoAbrigoPorId() throws Exception {
        //ARRANGE
        String id = "1";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{id}/pets",id)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo404ParaRequisicaoDeListarPetsDoAbrigoPorIdInvalido() throws Exception {
        //ARRANGE
        String id = "1";
        given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{id}/pets",id)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo404ParaRequisicaoDeListarPetsDoAbrigoPorNomeInvalido() throws Exception {
        //ARRANGE
        String nome = "Miau";
        given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                get("/abrigos/{nome}/pets",nome)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeCadastrarPetPeloId() throws Exception {
        //ARRANGE
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoId = "1";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoId}/pets",abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeCadastrarPetPeloNome() throws Exception {
        //ARRANGE
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoNome = "Abrigo feliz";

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoNome}/pets",abrigoNome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo404ParaRequisicaoDeCadastrarPetAbrigoNaoEncontradoPeloId() throws Exception {
        //ARRANGE
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoId = "1";

        given(abrigoService.carregarAbrigo(abrigoId)).willThrow(ValidacaoException.class);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoId}/pets",abrigoId)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404,response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo404ParaRequisicaoDeCadastrarPetAbrigoNaoEncontradoPeloNome() throws Exception {
        //ARRANGE
        String json = """
                {
                    "tipo": "GATO",
                    "nome": "Miau",
                    "raca": "padrao",
                    "idade": "5",
                    "cor" : "Parda",
                    "peso": "6.4"
                }
                """;

        String abrigoNome = "Abrigo legal";

        given(abrigoService.carregarAbrigo(abrigoNome)).willThrow(ValidacaoException.class);

        //ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/abrigos/{abrigoNome}/pets",abrigoNome)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(404,response.getStatus());
    }


}