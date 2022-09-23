package com.softwarehouse.serviceorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformation {

    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String gender;
    private String otherInformations;

    private String situation;

    private String fantasyName;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;

    private String contactType;
    private String contactName;
    private String contactPhone;




}
