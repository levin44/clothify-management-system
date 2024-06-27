package org.example.entity;

import jakarta.persistence.*;
import javafx.beans.property.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "orderDetails")
public class Orders {
    @Id
    private String orderId;

    private String paymentType;
    private Double totalCost;
    private Date orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails;

    // JavaFX properties
    public StringProperty orderIdProperty() {
        return new SimpleStringProperty(orderId);
    }

    public StringProperty paymentTypeProperty() {
        return new SimpleStringProperty(paymentType);
    }

    public DoubleProperty totalCostProperty() {
        return new SimpleDoubleProperty(totalCost);
    }

    public ObjectProperty<Date> orderDateProperty() {
        return new SimpleObjectProperty<>(orderDate);
    }

    public StringProperty employeeNameProperty() {
        return new SimpleStringProperty(user != null ? user.getName() : "N/A");
    }

    public StringProperty customerNameProperty() {
        return new SimpleStringProperty(customer.getName());
    }
}
