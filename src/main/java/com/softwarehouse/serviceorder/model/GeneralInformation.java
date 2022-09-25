package com.softwarehouse.serviceorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformation {


    private String name;
    private String email;
    private String cpf;
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    private LocalDate birthDate;


    private String fantasyName;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;

    private String contactName;
    private String contactPhone;

    @ManyToOne
    @JoinColumn(name = "contact_type_id")
    private ContactType contactType;

    private String otherInformations;


    @Enumerated(EnumType.STRING)
    @Column(name = "situation", nullable = false)
    private Situation situation;


}
