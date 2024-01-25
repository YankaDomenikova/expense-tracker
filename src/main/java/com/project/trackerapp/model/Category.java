package com.project.trackerapp.model;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String categoryName;

    private String baseColor;

    private String accentColor;

    private String icon;

    private String categoryType;

    private String chartColor;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    public Category() {}

    public Category(long id, String categoryName, String baseColor, String accentColor, String icon, String categoryType, String chartColor, List<Transaction> transactions){
        this.id = id;
        this.categoryName = categoryName;
        this.baseColor = baseColor;
        this.accentColor = accentColor;
        this.icon = icon;
        this.categoryType = categoryType;
        this.chartColor = chartColor;
        this.transactions = transactions;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getBaseColor() { return baseColor; }

    public void setBaseColor(String baseColor) { this.baseColor = baseColor; }

    public String getAccentColor() { return accentColor;  }

    public void setAccentColor(String accentColor) { this.accentColor = accentColor; }

    public String getIcon() { return this.icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public String getCategoryType() { return categoryType;  }

    public void setCategoryType(String categoryType) { this.categoryType = categoryType; }

    public String getChartColor() { return chartColor; }

    public void setChartColor(String chartColor) { this.chartColor = chartColor; }

    public List<Transaction> getTransactions() {  return transactions; }

    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions;  }


}
