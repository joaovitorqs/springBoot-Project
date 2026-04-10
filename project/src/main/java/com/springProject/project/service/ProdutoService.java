package com.springProject.project.service;

import com.springProject.project.exception.RecursoNaoEncontradoException;
import com.springProject.project.model.Produto;
import com.springProject.project.repository.ProdutoRepository;
import org.antlr.v4.runtime.RecognitionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com id: " + id));

    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }
    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com id: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
