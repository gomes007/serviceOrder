package com.softwarehouse.serviceorder.contexts.shared.entities;

import com.softwarehouse.serviceorder.contexts.shared.models.CompanyType;
import com.softwarehouse.serviceorder.contexts.shared.models.Situation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "situation", nullable = false)
    private Situation situation;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", nullable = false)
    private CompanyType companyType;
}
