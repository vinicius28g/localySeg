package com.seguranca.localyseg.dto;

import com.seguranca.localyseg.models.Pessoa;

public record UsuarioRetorno(long id, String login, String role, Pessoa pessoa) {

}
