package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.entity.Book;
import com.example.bookmanagementsystem.entity.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
public class PdfExportService {

    @Autowired
    private AnalyticsService analyticsService;

    public byte[] exportToPdf(List<User> users, List<Book> books) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Main Title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
        Paragraph mainTitle = new Paragraph("Stockholms Library Report", titleFont);
        mainTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(mainTitle);
        document.add(new Paragraph(" ")); // Add a blank line

        //Subtitle
        Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph subtitle = new Paragraph("Analytics", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_LEFT); // Center align the subtitle
        document.add(subtitle);
        document.add(new Paragraph(" ")); // Add a blank line

        //Analytics Info
        addAnalyticsInfo(document);

        //User Details
        document.add(new Paragraph("User   Details:", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
        PdfPTable userTable = new PdfPTable(3); // 3 columns for username, email, role
        userTable.setWidthPercentage(100);
        userTable.setSpacingBefore(10f);
        userTable.setSpacingAfter(10f);

        // Table headers
        addTableHeader(userTable, "Username");
        addTableHeader(userTable, "Email");
        addTableHeader(userTable, "Role");

        for (User  user : users) {
            userTable.addCell(user.getUsername());
            userTable.addCell(user.getEmail());
            userTable.addCell(user.getRole()); // Convert Role to String
        }
        document.add(userTable);

        //Book Details
        document.add(new Paragraph("Book Details:", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
        PdfPTable bookTable = new PdfPTable(3); // Adjust columns as needed
        bookTable.setWidthPercentage(100);
        bookTable.setSpacingBefore(10f);
        bookTable.setSpacingAfter(10f);

        // Table headers
        addTableHeader(bookTable, "Title");
        addTableHeader(bookTable, "Author");
        addTableHeader(bookTable, "ISBN");

        for (Book book : books) {
            bookTable.addCell(book.getTitle());
            bookTable.addCell(book.getAuthor());
            bookTable.addCell(book.getIsbn());
        }
        document.add(bookTable);

        document.close();
        return outputStream.toByteArray();
    }

    private void addAnalyticsInfo(Document document) throws DocumentException {
        // Fetch analytics data
        int totalBooks = analyticsService.getTotalNumberOfBooks();
        int totalStock = analyticsService.getTotalStock();
        List<Book> lowStockBooks = analyticsService.getLowStockBooks(5); // Example threshold
        List<Book> mostBorrowedBooks = analyticsService.getMostBorrowedBooks();

        // Add Analytics Info to PDF
        document.add(new Paragraph("Total Number of Books: " + totalBooks, new Font(Font.FontFamily.HELVETICA, 14)));
        document.add(new Paragraph("Total Stock of All Books: " + totalStock, new Font(Font.FontFamily.HELVETICA, 14)));
        document.add(new Paragraph("Books with Low Stock: " + lowStockBooks.size(), new Font(Font.FontFamily.HELVETICA, 14)));

        // Add details of low stock books
        if (!lowStockBooks.isEmpty()) {
            document.add(new Paragraph("Low Stock Books:", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));
            for (Book book : lowStockBooks) {
                document.add(new Paragraph(book.getTitle() + " (Stock: " + book.getStock() + ")", new Font(Font.FontFamily.HELVETICA, 12)));
            }
        }

        document.add(new Paragraph("Most Borrowed Books: " + mostBorrowedBooks.size(), new Font(Font.FontFamily.HELVETICA, 14)));

        // Add details of most borrowed books
        if (!mostBorrowedBooks.isEmpty()) {
            document.add(new Paragraph("Most Borrowed Books:", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));
            for (Book book : mostBorrowedBooks) {
                document.add(new Paragraph(book.getTitle(), new Font(Font.FontFamily.HELVETICA, 12)));
            }
        }
    }

    private void addTableHeader(PdfPTable table, String header) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Phrase(header, font));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(10);
        table.addCell(cell);
    }
}
