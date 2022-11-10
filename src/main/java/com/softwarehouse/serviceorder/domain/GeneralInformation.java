package com.softwarehouse.serviceorder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformation {
    private String name;

    @Column(nullable = false, unique = true, length = 170)
    private String email;

    @Column(unique = true, length = 11)
    private String cpf;

    private String phone;

    private String otherInformations;

    private String fantasyName;

    @Column(unique = true, length = 14)
    private String cnpj;

    private String razaoSocial;
    private String inscricaoEstadual;

    private String contactName;
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation", nullable = false)
    private Situation situation;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", nullable = false)
    private CompanyType companyType;
}
