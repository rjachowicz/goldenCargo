package com.goldencargo.model.dto.web;

import com.goldencargo.model.entities.Client;
import com.goldencargo.model.entities.ClientInvoice;
import com.goldencargo.model.entities.ClientOrder;
import com.goldencargo.model.entities.Goods;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientReportDTO {
    private Client client;
    private List<ClientOrder> orders;
    private List<ClientInvoice> invoices;
    private List<Goods> goods;
}
