package model;

public class FinancialReport {
    private String date;
    private String month;
    private String fromDate;
    private String toDate;

    private int totalTransactions;
    private double totalRevenue;
    private double totalTax;
    private double netRevenue;
    private String mostActiveCustomer;
    private String highestValueTransaction;

    private boolean summaryStatistics;
    private boolean exportableToPdf;

    // setters and getters

    public void setDate(String date) { this.date = date; }
    public String getDate() { return date; }

    public void setMonth(String month) { this.month = month; }
    public String getMonth() { return month; }

    public void setFromDate(String fromDate) { this.fromDate = fromDate; }
    public String getFromDate() { return fromDate; }

    public void setToDate(String toDate) { this.toDate = toDate; }
    public String getToDate() { return toDate; }

    public void setTotalTransactions(int totalTransactions) { this.totalTransactions = totalTransactions; }
    public int getTotalTransactions() { return totalTransactions; }

    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    public double getTotalRevenue() { return totalRevenue; }

    public void setTotalTax(double totalTax) { this.totalTax = totalTax; }
    public double getTotalTax() { return totalTax; }

    public void setNetRevenue(double netRevenue) { this.netRevenue = netRevenue; }
    public double getNetRevenue() { return netRevenue; }

    public void setMostActiveCustomer(String mostActiveCustomer) { this.mostActiveCustomer = mostActiveCustomer; }
    public String getMostActiveCustomer() { return mostActiveCustomer; }

    public void setHighestValueTransaction(String highestValueTransaction) { this.highestValueTransaction = highestValueTransaction; }
    public String getHighestValueTransaction() { return highestValueTransaction; }

    public void setSummaryStatistics(boolean summaryStatistics) { this.summaryStatistics = summaryStatistics; }
    public boolean hasSummaryStatistics() { return summaryStatistics; }

    public void setExportableToPdf(boolean exportableToPdf) { this.exportableToPdf = exportableToPdf; }
    public boolean isExportableToPdf() { return exportableToPdf; }
}
