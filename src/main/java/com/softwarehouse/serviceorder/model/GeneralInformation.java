package com.softwarehouse.serviceorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformation {
    private String name;
    private String email;
    private String cpf;
    private String phone;

    private String otherInformations;

    private String fantasyName;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;

    private String contactName;
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation", nullable = false)
    private Situation situation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;
}
