package com.immmus.infrastructure.api.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
public class MenuPosition implements Position {
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "menu_positions_seq", sequenceName = "menu_positions_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_positions_seq")

//  See https://hibernate.atlassian.net/browse/HHH-3718 and https://hibernate.atlassian.net/browse/HHH-12034
//  Proxy initialization when accessing its identifier managed now by JPA_PROXY_COMPLIANCE setting
    private Integer id;

    private double price;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;

    protected MenuPosition() { }

    private MenuPosition(final double price,
                         final String name,
                         final Category category,
                         final String description) {

        this.price = price;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public double price() {
        return this.price;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Category category() {
        return this.category;
    }

    @Override
    public String description() {
        return this.description;
    }

    public static Position.Builder<MenuPosition> builder() {
        return new PositionBuilder();
    }

    public final static class PositionBuilder implements Position.Builder<MenuPosition> {
        private double price;
        private String name;
        private Category category;
        private String description;

        @Override
        public Builder<? extends Position> price(double price) {
            this.price = price;
            return this;
        }

        @Override
        public Builder<? extends Position> name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder<? extends Position> category(Category category) {
            this.category = category;
            return this;
        }

        @Override
        public Builder<? extends Position> description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public MenuPosition create() {
            return new MenuPosition(price, name, category, description);
        }
    }
}
