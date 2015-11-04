package rs.paragraf.se.calc.interest.utils;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import rs.paragraf.se.calc.interest.data.AccountBean;
import rs.paragraf.se.calc.interest.data.AccountResultBean;
import rs.paragraf.se.calc.interest.ui.MainFrame;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;


public class ItextPrintUtil {

    private static ItextPrintUtil instance = null;
    static BaseFont bf = ResourceManager.getBaseFont();
    static Phrase footerLabel;

	public static ItextPrintUtil getInstance() {

    	if (instance==null)
    		instance=new ItextPrintUtil();

    	return instance;
    }

    public ItextPrintUtil() {
    	System.out.println("print util constructor");
    }

    public void printPreview() {
    	try {
    		File file = createHTML();
    		if (file != null)
    			//Desktop.getDesktop().open(file);
    			Desktop.getDesktop().open(new File(file.getCanonicalPath()));
		} catch (Exception e) {
			MainFrame.showMessage(MainFrame.properties.getProperty("print.message"), JOptionPane.ERROR_MESSAGE);
		}
    }

    public void printHTML() {
    	try {
    		File file = createHTML();
    		if (file != null)
    			//Desktop.getDesktop().print(file);
    			Desktop.getDesktop().print(new File(file.getCanonicalPath()));
		} catch (Exception e) {
			MainFrame.showMessage(MainFrame.properties.getProperty("print.message"), JOptionPane.ERROR_MESSAGE);
		}
    }

	public File createHTML()  {
		Document document = new Document();
		File resultFile = null;
		FileOutputStream fos = null;
		boolean again = true;
		int counter=-1;
		while (again && counter<100)
		{
			try {
				counter++;
				resultFile = new File(MainFrame.RUNTIME_PATH
						+ "picalc/document" + System.currentTimeMillis() + ".pdf");
				fos = new FileOutputStream(resultFile);
				again=false;
			} catch (Exception e) {System.out.println(e.getMessage()); again=true;	}
		}

		try {

			if (MainFrame.getInstance().getInterestCalculationPanel().getAccountPanel().getTable().getCellEditor() != null)
				MainFrame.getInstance().getInterestCalculationPanel().getAccountPanel().getTable().getCellEditor().stopCellEditing();
			else
				System.out.println("cell editor null");

			// create account bean
			AccountBean account  = MainFrame.getInstance().interestCalculationPanel.readForm();

			// validate account bean, if not valid print preview is not possible!
			switch (account.validateForHtml()) {
				case INVALID_FROM_DATE: {
					MainFrame.showMessage(
							MainFrame.properties.getProperty("print.preview.account.invalid.from.date"),
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				case INVALID_TO_DATE: {
					MainFrame.showMessage(
							MainFrame.properties.getProperty("print.preview.account.invalid.to.date"),
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				case FROM_DATE_AFTER_TO_DATE: {
					MainFrame.showMessage(
							MainFrame.properties.getProperty("print.preview.account.invalid.from.date.after.to.date"),
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				case INVALID_START_AMOUNT: {
					MainFrame.showMessage(
							MainFrame.properties.getProperty("print.preview.account.invalid.start.amount"),
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				case INVALID_CREDITOR_DATA: {
					if (MainFrame.getInstance().configPanel.isUsingUserConfing())
						{
						MainFrame.showMessage(
							MainFrame.properties.getProperty("print.preview.account.invalid.creditor.data"),
							JOptionPane.ERROR_MESSAGE);
					return null;
						}
					break;
				}
				case INVALID_DEBTOR_DATA: {
					if (MainFrame.getInstance().configPanel.isUsingUserConfing())
					{
					MainFrame.showMessage(
							MainFrame.properties.getProperty("print.preview.account.invalid.debtor.data"),
							JOptionPane.ERROR_MESSAGE);
					return null;
					}
					break;
				}
				case INVALID_ACCOUNT_CHANGE_TABLE: {
					MainFrame.showMessage(
							MainFrame.properties.getProperty("print.preview.account.invalid.account.change.table"),
							JOptionPane.ERROR_MESSAGE);
					return null;
				}
				default: {
					System.out.println("****** DATA VALID FOR HTML ******");
					break;
				}
			}

			MainFrame.getInstance().getInterestCalculationPanel().
				getAccountPanel().getNumberCellRenderer().setPaintBackground(false);

			account.calculate();

//			if (account.getTotalDebt()==0) return null;

			MainFrame.getInstance().getInterestCalculationPanel().getMainPanel().getInterest().setText(
					MainFrame.decimalFormater.format(account.getTotalInterest()));
			MainFrame.getInstance().getInterestCalculationPanel().getMainPanel().getTotal().setText(
					MainFrame.decimalFormater.format(account.getTotalDebt()));

			// step 1
			//document = new Document();
			footerLabel = createAndStylePhraseLabel("page.footer.label", bf);

			// step 2
			PdfWriter writer = PdfWriter.getInstance(document, fos);

			String pageSizeName = MainFrame.printProperties.getProperty("document.page.size");
			ItextPrintUtil.getInstance().setPageSizeToDocument(document, pageSizeName);
			document.setMargins(
					Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.left")),
					Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.right")),
					Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.top")),
					Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.bottom")));

			writer.setBoxSize("art", new Rectangle(
					Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.left")),
					Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.bottom")),
					Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.left")) + PageSize.A4.RIGHT,
					Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.bottom")) + PageSize.A4.TOP));

			HeaderFooter event = new HeaderFooter();
			writer.setPageEvent(event);

			// step 3
			document.open();

			// document title
			Paragraph documentTitle = createAndStyleParagraphLabel("document.title", bf);
			documentTitle.setAlignment(Element.ALIGN_CENTER);
			documentTitle.setSpacingAfter(20);

			// print date
			Paragraph printDateLabel = createAndStyleParagraphLabel("document.printDateLabel", bf);
			Paragraph printDate = createAndStyleParagraph(
					"document.printDateLabel",
					MainFrame.getInstance().sdfWithPoints.format(Calendar.getInstance().getTime()), bf);
			printDateLabel.add(printDate);
			printDateLabel.setSpacingAfter(25);

			PdfPTable cdTable = new PdfPTable(2);
			PdfPTable creditorTable = new PdfPTable(1);
			PdfPTable debtorTable = new PdfPTable(1);

			// creditor and debtor table cells
			PdfPCell creditorTableCell = createAndStyleTableCell(
					creditorTable, ResourceManager.getBaseColor("creditorTable.cell.background"), false);
			creditorTableCell.setBorder(
					Integer.parseInt(MainFrame.printProperties.getProperty("creditorTable.cell.border")));
			PdfPCell debtorTableCell = createAndStyleTableCell(
					debtorTable, ResourceManager.getBaseColor("debtorTable.cell.background"), false);
			debtorTableCell.setBorder(
					Integer.parseInt(MainFrame.printProperties.getProperty("debtorTable.cell.border")));

			cdTable.addCell(creditorTable);
			cdTable.addCell(debtorTable);
			cdTable.setWidthPercentage(100);
			cdTable.setSpacingAfter(20);

			// creditor table cells
			Paragraph creditorLabel = createAndStyleParagraphLabel("creditor.label", bf);
			PdfPCell creditorLabelCell = createAndStyleTableCell(
					creditorLabel, ResourceManager.getBaseColor("creditor.label.cell.background"), true);
			creditorLabelCell.setBorder(
					Integer.parseInt(MainFrame.printProperties.getProperty("creditor.label.cell.border")));

			PdfPTable innerCreditorTable = new PdfPTable(2);
			innerCreditorTable.setWidths(new int[] { 1, 3 });
			creditorTable.addCell(creditorLabelCell);
			PdfPCell innerCreditorTableCell = new PdfPCell(innerCreditorTable);
			innerCreditorTableCell.setBorder(
					Integer.parseInt(MainFrame.printProperties.getProperty("innerCreditorTable.cell.border")));
			creditorTable.addCell(innerCreditorTableCell);
			// inner creditor table cells
			createCreditorInnerTables(bf, innerCreditorTable, account);

			// debtor table cells
			Paragraph debtorLabel = createAndStyleParagraphLabel("debtor.label", bf);
			PdfPCell debtorLabelCell = createAndStyleTableCell(
					debtorLabel, ResourceManager.getBaseColor("debtor.label.cell.background"), true);
			debtorLabelCell.setBorder(
					Integer.parseInt(MainFrame.printProperties.getProperty("debtor.label.cell.border")));

			PdfPTable innerDebtorTable = new PdfPTable(2);
			innerDebtorTable.setWidths(new int[] { 1, 3 });
			debtorTable.addCell(debtorLabelCell);
			PdfPCell innerDebtorTableCell = new PdfPCell(innerDebtorTable);
			innerDebtorTableCell.setBorder(
					Integer.parseInt(MainFrame.printProperties.getProperty("innerDebtorTable.cell.border")));
			debtorTable.addCell(innerDebtorTableCell);
			// inner debtor table cells
			createDebtorInnerTables(bf, innerDebtorTable, account);
			String currency = MainFrame.printProperties.getProperty("document.calculatePeriod.label4");
			if (account.getInterestType()==AccountBean.ECB_INTEREST_TYPE ||
					account.getInterestType()==AccountBean.LAW_INTEREST_TYPE_EUR	)
				currency = MainFrame.printProperties.getProperty("document.calculatePeriod.label4a");
			// calculate period
			String calculatePeriodLabel = new String(
					MainFrame.printProperties.getProperty("document.calculatePeriod.label1") +
					MainFrame.sdfWithPoints.format(account.getFrom()) + " " +
					MainFrame.printProperties.getProperty("document.calculatePeriod.label2") +
					MainFrame.sdfWithPoints.format(account.getTo()) + " " +
					MainFrame.printProperties.getProperty("document.calculatePeriod.label3") +
					MainFrame.decimalFormater.format(account.getStartAmount()) + " " +
					currency);
			Paragraph calculatePeriod = createAndStyleParagraph(
					"document.calculatePeriod.label", calculatePeriodLabel, bf);
			calculatePeriod.setAlignment(Element.ALIGN_LEFT);
			calculatePeriod.setSpacingAfter(25);

			// document number
			Paragraph documentNumber = createAndStyleParagraph(
					"document.number.label",
					MainFrame.printProperties.getProperty("document.number.label") + account.getDocument(),
					bf);
			documentNumber.setAlignment(Element.ALIGN_LEFT);
			documentNumber.setSpacingAfter(10);

			// applied rate
			Paragraph appliedRate = createAndStyleParagraph(
					"document.appliedRate.label",
					MainFrame.printProperties.getProperty("document.appliedRate.label") +
						getInterestTypeByName(account),
					bf);
			appliedRate.setAlignment(Element.ALIGN_LEFT);
			appliedRate.setSpacingAfter(10);

			// applied method
			Paragraph appliedMethod = createAndStyleParagraph(
					"document.appliedRate.label",
					MainFrame.printProperties.getProperty("document.appliedMethod.label") +
						getMethodTypeByName(account),
					bf);
			appliedMethod.setAlignment(Element.ALIGN_LEFT);
			appliedMethod.setSpacingAfter(10);

			// applied method
			Paragraph apliedCalculationType = createAndStyleParagraph(
					"document.appliedRate.label",
					MainFrame.printProperties.getProperty("document.apliedCalculationType.label") +
						getCalculationTypeByName(account),
					bf);
			apliedCalculationType.setAlignment(Element.ALIGN_LEFT);
			apliedCalculationType.setSpacingAfter(10);

			PdfPTable accountResultTable = createAccountResultTable(bf, account);

			document.add(documentTitle);
			document.add(calculatePeriod);
			document.add(cdTable);
			document.add(documentNumber);
			document.add(appliedRate);
			document.add(appliedMethod);
			document.add(apliedCalculationType);
			document.add(printDateLabel);
			document.add(accountResultTable);

			// step 5
//			resultFile = new File("conf/htmlDocs/document.pdf");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			document.close();
			try {
				fos.close();
			} catch (IOException e) {
				System.out.println("Unable to close file output stream!");
			}
		}
		return resultFile;
	}

	/** Inner class to add a footer. */
	static class HeaderFooter extends PdfPageEventHelper {

		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle rect = writer.getBoxSize("art");

			System.out.println("footer font size: " + footerLabel.getFont().getCalculatedSize());

			ColumnText.showTextAligned(
					writer.getDirectContent(),
					Element.ALIGN_LEFT,
					//new Phrase(label),
					footerLabel,
					rect.getLeft(),
					rect.getBottom() -
						Integer.parseInt(MainFrame.printProperties.getProperty("document.page.margin.bottom"))/2+5, 0);
					//rect.getBottom() - 30, 0);
		}
	}

	private Paragraph createAndStyleParagraphLabel(String label, BaseFont bf) {
		String paragraphString = new String(MainFrame.printProperties.getProperty(label));
		Font paragraphFont = new Font(bf,
				Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.size")),
				Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.style")),
				new BaseColor(Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.color"))));

		Paragraph paragraph = new Paragraph(paragraphString, paragraphFont);

		return paragraph;
	}

	public static Phrase createAndStylePhraseLabel(String label, BaseFont bf) {
		String phraseString = new String(MainFrame.printProperties.getProperty(label));
		Font phraseFont = new Font(bf,
				Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.size")),
				Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.style")),
				new BaseColor(Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.color"))));

		Phrase phrase = new Phrase(phraseString, phraseFont);

		return phrase;
	}

	private Paragraph createAndStyleParagraph(String label, String value, BaseFont bf) {
		String paragraphString = new String(value);
		Font paragraphFont = new Font(bf,
				Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.size")),
				Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.style")),
				new BaseColor(Integer.parseInt(MainFrame.printProperties.getProperty(label + ".font.color"))));

		Paragraph paragraph = new Paragraph(paragraphString, paragraphFont);

		return paragraph;
	}

	private PdfPCell createAndStyleTableCell(
			Element cellElement, BaseColor backgroundColor, boolean vAlign) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(backgroundColor);
		if (vAlign) {
			cell.setUseAscender(true);
			cell.setUseDescender(true);
		}
		cell.addElement(cellElement);
		return cell;
	}

	public PdfPTable createCreditorInnerTables(BaseFont bf, PdfPTable innerTable, AccountBean account) {
		// name
		Paragraph creditorNameLabel = createAndStyleParagraph(
				 "creditor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.name.label"), bf);
		PdfPCell nameLabelCell = createAndStyleTableCell(
				creditorNameLabel, ResourceManager.getBaseColor("creditor.data.label.cell.background"), true);
		// fill with data from creditor bean
		Paragraph creditorName = createAndStyleParagraph(
				"creditor.data", account.getCreditor().getName(), bf);
		PdfPCell nameCell = createAndStyleTableCell(
				creditorName, ResourceManager.getBaseColor("creditor.data.cell.background"), true);

		// address
		Paragraph creditorAddressLabel = createAndStyleParagraph(
				"creditor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.address.label"), bf);
		PdfPCell addressLabelCell = createAndStyleTableCell(
				creditorAddressLabel, ResourceManager.getBaseColor("creditor.data.label.cell.background"), true);
		// fill with data from creditor bean
		Paragraph creditorAddress = createAndStyleParagraph(
				"creditor.data", account.getCreditor().getAddress(), bf);
		PdfPCell addressCell = createAndStyleTableCell(
				creditorAddress, ResourceManager.getBaseColor("creditor.data.cell.background"), true);

		// city
		Paragraph creditorCityLabel = createAndStyleParagraph(
				"creditor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.city.label"), bf);
		PdfPCell cityLabelCell = createAndStyleTableCell(
				creditorCityLabel, ResourceManager.getBaseColor("creditor.data.label.cell.background"), true);
		// fill with data from creditor bean
		Paragraph creditorCity = createAndStyleParagraph(
				"creditor.data", account.getCreditor().getCity(), bf);
		PdfPCell cityCell = createAndStyleTableCell(
				creditorCity, ResourceManager.getBaseColor("creditor.data.cell.background"), true);

		// PIB
		Paragraph creditorPibLabel = createAndStyleParagraph(
				"creditor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.pib.label"), bf);
		PdfPCell pibLabelCell = createAndStyleTableCell(
				creditorPibLabel, ResourceManager.getBaseColor("creditor.data.label.cell.background"), true);
		// fill with data from creditor bean
		Paragraph creditorPib = createAndStyleParagraph(
				"creditor.data", account.getCreditor().getPib(), bf);
		PdfPCell pibCell = createAndStyleTableCell(
				creditorPib, ResourceManager.getBaseColor("creditor.data.cell.background"), true);

		// phone
		Paragraph creditorPhoneLabel = createAndStyleParagraph(
				"creditor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.phone.label"), bf);
		PdfPCell phoneLabelCell = createAndStyleTableCell(
				creditorPhoneLabel, ResourceManager.getBaseColor("creditor.data.label.cell.background"), true);
		// fill with data from creditor bean
		Paragraph creditorPhone = createAndStyleParagraph(
				"creditor.data", account.getCreditor().getPhone(), bf);
		PdfPCell phoneCell = createAndStyleTableCell(
				creditorPhone, ResourceManager.getBaseColor("creditor.data.cell.background"), true);

		innerTable.addCell(nameLabelCell);
		innerTable.addCell(nameCell);
		innerTable.addCell(addressLabelCell);
		innerTable.addCell(addressCell);
		innerTable.addCell(cityLabelCell);
		innerTable.addCell(cityCell);
		innerTable.addCell(pibLabelCell);
		innerTable.addCell(pibCell);
		innerTable.addCell(phoneLabelCell);
		innerTable.addCell(phoneCell);

		return innerTable;
	}

	public PdfPTable createDebtorInnerTables(BaseFont bf, PdfPTable innerTable, AccountBean account) {
		// name
		Paragraph debtorNameLabel = createAndStyleParagraph(
				"debtor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.name.label"), bf);
		PdfPCell nameLabelCell = createAndStyleTableCell(
				debtorNameLabel, ResourceManager.getBaseColor("debtor.data.label.cell.background"), true);
		// fill with data from debtor bean
		Paragraph debtorName = createAndStyleParagraph(
				"debtor.data", account.getDebtor().getName(), bf);
		PdfPCell nameCell = createAndStyleTableCell(
				debtorName, ResourceManager.getBaseColor("debtor.data.cell.background"), true);

		// address
		Paragraph debtorAddressLabel = createAndStyleParagraph(
				"debtor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.address.label"), bf);
		PdfPCell addressLabelCell = createAndStyleTableCell(
				debtorAddressLabel, ResourceManager.getBaseColor("debtor.data.label.cell.background"), true);
		// fill with data from debtor bean
		Paragraph debtorAddress = createAndStyleParagraph(
				"debtor.data", account.getDebtor().getAddress(), bf);
		PdfPCell addressCell = createAndStyleTableCell(
				debtorAddress, ResourceManager.getBaseColor("debtor.data.cell.background"), true);

		// city
		Paragraph debtorCityLabel = createAndStyleParagraph(
				"debtor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.city.label"), bf);
		PdfPCell cityLabelCell = createAndStyleTableCell(
				debtorCityLabel, ResourceManager.getBaseColor("debtor.data.label.cell.background"), true);
		// fill with data from debtor bean
		Paragraph debtorCity = createAndStyleParagraph(
				"debtor.data", account.getDebtor().getCity(), bf);
		PdfPCell cityCell = createAndStyleTableCell(
				debtorCity, ResourceManager.getBaseColor("debtor.data.cell.background"), true);

		// PIB
		Paragraph debtorPibLabel = createAndStyleParagraph(
				"debtor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.pib.label"), bf);
		PdfPCell pibLabelCell = createAndStyleTableCell(
				debtorPibLabel, ResourceManager.getBaseColor("debtor.data.label.cell.background"), true);
		// fill with data from debtor bean
		Paragraph debtorPib = createAndStyleParagraph(
				"debtor.data", account.getDebtor().getPib(), bf);
		PdfPCell pibCell = createAndStyleTableCell(
				debtorPib, ResourceManager.getBaseColor("debtor.data.cell.background"), true);

		// phone
		Paragraph debtorPhoneLabel = createAndStyleParagraph(
				"debtor.data.label", MainFrame.printProperties.getProperty("creditorAndDebtor.phone.label"), bf);
		PdfPCell phoneLabelCell = createAndStyleTableCell(
				debtorPhoneLabel, ResourceManager.getBaseColor("debtor.data.label.cell.background"), true);
		// fill with data from debtor bean
		Paragraph debtorPhone = createAndStyleParagraph(
				"debtor.data", account.getDebtor().getPhone(), bf);
		PdfPCell phoneCell = createAndStyleTableCell(
				debtorPhone, ResourceManager.getBaseColor("debtor.data.cell.background"), true);

		innerTable.addCell(nameLabelCell);
		innerTable.addCell(nameCell);
		innerTable.addCell(addressLabelCell);
		innerTable.addCell(addressCell);
		innerTable.addCell(cityLabelCell);
		innerTable.addCell(cityCell);
		innerTable.addCell(pibLabelCell);
		innerTable.addCell(pibCell);
		innerTable.addCell(phoneLabelCell);
		innerTable.addCell(phoneCell);

		return innerTable;
	}

	public PdfPTable createAccountResultTable(BaseFont bf, AccountBean account) {
		// Create a table with 9 columns
		PdfPTable table = new PdfPTable(new float[] {
				1, 1, 1.1f, 0.8f, 0.7f, 1f, 1.1f, 1f, 1, 1.1f });
		table.setWidthPercentage(100f);
		table.getDefaultCell().setUseAscender(true);
		table.getDefaultCell().setUseDescender(true);

		// Add header row
		//table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
		for (int i = 0; i < 1; i++) {
			Paragraph dateFromHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.dateFrom"), bf);
			PdfPCell dateFromCell = new PdfPCell(dateFromHeader);
			styleHeaderCellAccountResultTable(dateFromCell);
			table.addCell(dateFromCell);

			Paragraph dateToHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.dateTo"), bf);
			PdfPCell dateToCell = new PdfPCell(dateToHeader);
			styleHeaderCellAccountResultTable(dateToCell);
			table.addCell(dateToCell);

			Paragraph amountHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.amount"), bf);
			PdfPCell amountCell = new PdfPCell(amountHeader);
			styleHeaderCellAccountResultTable(amountCell);
			table.addCell(amountCell);

			Paragraph rateHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.rate"), bf);
			PdfPCell rateCell = new PdfPCell(rateHeader);
			styleHeaderCellAccountResultTable(rateCell);
			table.addCell(rateCell);

			Paragraph methodHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.method"), bf);
			PdfPCell methodCell = new PdfPCell(methodHeader);
			styleHeaderCellAccountResultTable(methodCell);
			table.addCell(methodCell);

			Paragraph interestHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.interest"), bf);
			PdfPCell interestCell = new PdfPCell(interestHeader);
			styleHeaderCellAccountResultTable(interestCell);
			table.addCell(interestCell);

			Paragraph interestChangeHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.interestChange"), bf);
			PdfPCell interestChangeCell = new PdfPCell(interestChangeHeader);
			styleHeaderCellAccountResultTable(interestChangeCell);
			table.addCell(interestChangeCell);

			Paragraph callToHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.callTo"), bf);
			PdfPCell callToCell = new PdfPCell(callToHeader);
			styleHeaderCellAccountResultTable(callToCell);
			table.addCell(callToCell);

			Paragraph dateOfChangeHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.dateOfChange"), bf);
			PdfPCell dateOfChangeCell = new PdfPCell(dateOfChangeHeader);
			styleHeaderCellAccountResultTable(dateOfChangeCell);
			table.addCell(dateOfChangeCell);

			Paragraph debtHeader = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.printProperties.getProperty("accountResultTable.debt"), bf);
			PdfPCell debtCell = new PdfPCell(debtHeader);
			styleHeaderCellAccountResultTable(debtCell);
			table.addCell(debtCell);
		}
		table.getDefaultCell().setBackgroundColor(null);
		// There is one special row
		table.setHeaderRows(1);
		// There is no footer
		table.setFooterRows(0);

		// get all accounts and loop over it
		for (AccountResultBean resultBean : account.getResults()) {

			if (MainFrame.getInstance().sdfWithPoints.format(resultBean.getFrom()) != null) {
				Paragraph dateFrom = createAndStyleParagraph(
						"accountResultTable.data",
						MainFrame.getInstance().sdfWithPoints.format(resultBean.getFrom()), bf);
				PdfPCell dateFromCell = new PdfPCell(dateFrom);
				dateFromCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				dateFromCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(dateFromCell);
			} else {
				table.addCell("");
			}
			if (MainFrame.getInstance().sdfWithPoints.format(resultBean.getTo()) != null) {
				Paragraph dateTo = createAndStyleParagraph(
						"accountResultTable.data",
						MainFrame.getInstance().sdfWithPoints.format(resultBean.getTo()), bf);
				PdfPCell dateToCell = new PdfPCell(dateTo);
				dateToCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				dateToCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(dateToCell);
			} else {
				table.addCell("");
			}

//			Paragraph amount = createAndStyleParagraph(
//					"accountResultTable.data",
//					new Double(resultBean.getAmount()).toString(), bf);
//			Paragraph amount = createAndStyleParagraph(
//					"accountResultTable.data",
//					MainFrame.decimalFormater.format(account.getStartAmount()), bf);
//
//			if (account.getMethodType()==AccountBean.CONFORM_METHOD_TYPE)
			Paragraph amount = createAndStyleParagraph(
						"accountResultTable.data",
						MainFrame.decimalFormater.format(resultBean.getAmount()), bf);

			PdfPCell amountCell = new PdfPCell(amount);
			amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			amountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(amountCell);
			//table.addCell(amount);

			Paragraph interestRate = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.rateFormater.format(resultBean.getInterestRate()), bf);
			PdfPCell interestRateCell = new PdfPCell(interestRate);
			interestRateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			interestRateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(interestRateCell);

			Paragraph methodRate = createAndStyleParagraph(
					"accountResultTable.data",
					methodToString(resultBean.getMethod()), bf);
			PdfPCell methodRateCell = new PdfPCell(methodRate);
			methodRateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			methodRateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(methodRateCell);

			Paragraph interest = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.decimalFormater.format(resultBean.getInterest()), bf);
			PdfPCell interestCell = new PdfPCell(interest);
			interestCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			interestCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(interestCell);

			Paragraph change = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.decimalFormater.format(resultBean.getChange()), bf);
			PdfPCell changeCell = new PdfPCell(change);
			changeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			changeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(changeCell);

			if (resultBean.getDocument() != null) {
				Paragraph document = createAndStyleParagraph(
						"accountResultTable.data",
						resultBean.getDocument(), bf);
				PdfPCell documentCell = new PdfPCell(document);
				documentCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				documentCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(documentCell);
			} else {
				table.addCell("");
			}

			if (resultBean.getChangeDate() != null) {
				Paragraph changeDate = createAndStyleParagraph(
						"accountResultTable.data",
						MainFrame.getInstance().sdfWithPoints.format(resultBean.getChangeDate()), bf);
				PdfPCell changeDateCell = new PdfPCell(changeDate);
				changeDateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				changeDateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(changeDateCell);
			} else {
				table.addCell("");
			}

			Paragraph debt = createAndStyleParagraph(
					"accountResultTable.data",
					MainFrame.decimalFormater.format(resultBean.getDebt()), bf);
			PdfPCell debtCell = new PdfPCell(debt);
			debtCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			debtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(debtCell);
		}

		// add empty row
		PdfPCell emptyCell = new PdfPCell();
		emptyCell.setFixedHeight(3);
		for (int i = 0; i < 10; ++i) {
			table.addCell(emptyCell);
		}

		// add total amount row
		Paragraph totalHeader = createAndStyleParagraph(
				"accountResultTable.total",
				MainFrame.printProperties.getProperty("accountResultTable.total"), bf);
		PdfPCell totalHeaderCell = new PdfPCell(totalHeader);
		totalHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		totalHeaderCell.setColspan(2);
		table.addCell(totalHeaderCell);

			//table.addCell("");

		Paragraph totalAmmount = createAndStyleParagraph(
				"accountResultTable.total",
				MainFrame.decimalFormater.format(account.getStartAmount()), bf);
		PdfPCell totalAmmountCell = new PdfPCell(totalAmmount);
		totalAmmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalAmmountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(totalAmmountCell);

		table.addCell("");
		table.addCell("");

		Paragraph totalInterest = createAndStyleParagraph(
				"accountResultTable.total",
				MainFrame.decimalFormater.format(account.getTotalInterest()), bf);
		PdfPCell totalInterestCell = new PdfPCell(totalInterest);
		totalInterestCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalInterestCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(totalInterestCell);

		Paragraph totalChange = createAndStyleParagraph(
				"accountResultTable.total",
				MainFrame.decimalFormater.format(account.getTotalChange()), bf);
		PdfPCell totalChangeCell = new PdfPCell(totalChange);
		totalChangeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalChangeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(totalChangeCell);

		for (int i = 0; i < 2; ++i)
			table.addCell("");

		Paragraph totalDebt = createAndStyleParagraph(
				"accountResultTable.total",
				MainFrame.decimalFormater.format(account.getTotalDebt()), bf);
		PdfPCell totalDebtCell = new PdfPCell(totalDebt);
		totalDebtCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalDebtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(totalDebtCell);

		return table;
	}

	private String methodToString(double method) {

		if (method==AccountBean.CONFORM_METHOD_TYPE) return MainFrame.properties.getProperty("conform.method.label");
		if (method==AccountBean.PROPORCIONAL_METHOD_TYPE) return MainFrame.properties.getProperty("proporcional.method.label");
		return "";
	}

	public void styleHeaderCellAccountResultTable(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(
				new BaseColor(
						Integer.parseInt(
								MainFrame.printProperties.getProperty(
										"accountResultTable.header.background"))));
	}

	public String getInterestTypeByName(AccountBean account)
	{
		if (account.getInterestType() == AccountBean.LAW_INTEREST_TYPE)
			return MainFrame.properties.getProperty("law.interest.type");
		if (account.getInterestType() == AccountBean.LAW_INTEREST_TYPE_EUR)
			return MainFrame.properties.getProperty("law.eur.interest.type");
		else if (account.getInterestType() == AccountBean.PRICE_GROWTH_INTEREST_TYPE)
			return MainFrame.properties.getProperty("price.growth.interest.type");
		else if (account.getInterestType() == AccountBean.TAX_INTEREST_TYPE)
			return MainFrame.properties.getProperty("tax.interest.type");
		else if (account.getInterestType() == AccountBean.FIXED_MONHLEY_CONTRACT_INTEREST_TYPE)
			return MainFrame.properties.getProperty("fixed.monthley.contract.interest.type");
		else if (account.getInterestType() == AccountBean.FIXED_ANUAL_CONTRACT_INTEREST_TYPE)
			return MainFrame.properties.getProperty("fixed.anual.contract.interest.type");
		else if (account.getInterestType() == AccountBean.FIXED_DAILY_CONTRACT_INTEREST_TYPE)
			return MainFrame.properties.getProperty("fixed.daily.contract.interest.type");
		else if (account.getInterestType() == AccountBean.FREE_CONTRACT_INTEREST_TYPE)
			return MainFrame.properties.getProperty("free.contract.interest.type");
		else if (account.getInterestType() == AccountBean.EXCONT_ANUAL_INTEREST_TYPE)
			return MainFrame.properties.getProperty("excont.anual.interest.type");
		else if (account.getInterestType() == AccountBean.REFERENT_INTEREST_TYPE)
			return MainFrame.properties.getProperty("referent.interest.type");
		else if (account.getInterestType() == AccountBean.ECB_INTEREST_TYPE)
			return MainFrame.properties.getProperty("ecb.interest.type");
//		else if (account.getInterestType() == AccountBean.EXCONT_MONTHLEY_INTEREST_TYPE)
//			return MainFrame.properties.getProperty("excont.monthley.interest.type");
		else
			return new String("");
	}

	public String getMethodTypeByName(AccountBean account)
	{
		if (account.getMethodType() == AccountBean.PROPORCIONAL_METHOD_TYPE)
			return MainFrame.properties.getProperty("proporcional.method.type");
		else if (account.getMethodType() == AccountBean.CONFORM_METHOD_TYPE)
			return MainFrame.properties.getProperty("conform.method.type");
		else if (account.getMethodType() == AccountBean.MIXED_METHOD_TYPE)
			return MainFrame.properties.getProperty("mixed.method.type");
		else
			return new String(" - ");
	}

	public String getCalculationTypeByName(AccountBean account)
	{
		if (account.getInterestType()==AccountBean.TAX_INTEREST_TYPE) return MainFrame.properties.getProperty("calculation.tax.type");;
		if (account.getCalculationType() == AccountBean.CALCULATION_TYPE.AMMOUNT_FIRST_CALCULATION_TYPE)
			return MainFrame.properties.getProperty("calculation.ammount.first.type");
		else if (account.getCalculationType() == AccountBean.CALCULATION_TYPE.INTEREST_FIRST_CALCULATION_TYPE)
			return MainFrame.properties.getProperty("calculation.interest.first.type");
		else
			return new String(" - ");
	}

	protected void setPageSizeToDocument(Document doc, String pageSize) {
		if (pageSize.equalsIgnoreCase("letter"))
			doc.setPageSize(PageSize.LETTER);
		else if (pageSize.equalsIgnoreCase("note"))
			doc.setPageSize(PageSize.NOTE);
		else if (pageSize.equalsIgnoreCase("legal"))
			doc.setPageSize(PageSize.LEGAL);
		else if (pageSize.equalsIgnoreCase("tabloid"))
			doc.setPageSize(PageSize.TABLOID);
		else if (pageSize.equalsIgnoreCase("executive"))
			doc.setPageSize(PageSize.EXECUTIVE);
		else if (pageSize.equalsIgnoreCase("postcard"))
			doc.setPageSize(PageSize.POSTCARD);
		else if (pageSize.equalsIgnoreCase("a0"))
			doc.setPageSize(PageSize.A0);
		else if (pageSize.equalsIgnoreCase("a1"))
			doc.setPageSize(PageSize.A1);
		else if (pageSize.equalsIgnoreCase("a2"))
			doc.setPageSize(PageSize.A2);
		else if (pageSize.equalsIgnoreCase("a3"))
			doc.setPageSize(PageSize.A3);
		else if (pageSize.equalsIgnoreCase("a4"))
			doc.setPageSize(PageSize.A4);
		else if (pageSize.equalsIgnoreCase("a5"))
			doc.setPageSize(PageSize.A5);
		else if (pageSize.equalsIgnoreCase("a6"))
			doc.setPageSize(PageSize.A6);
		else if (pageSize.equalsIgnoreCase("a7"))
			doc.setPageSize(PageSize.A7);
		else if (pageSize.equalsIgnoreCase("a8"))
			doc.setPageSize(PageSize.A8);
		else if (pageSize.equalsIgnoreCase("a9"))
			doc.setPageSize(PageSize.A9);
		else if (pageSize.equalsIgnoreCase("a10"))
			doc.setPageSize(PageSize.A10);
		else if (pageSize.equalsIgnoreCase("b0"))
			doc.setPageSize(PageSize.B0);
		else if (pageSize.equalsIgnoreCase("b1"))
			doc.setPageSize(PageSize.B1);
		else if (pageSize.equalsIgnoreCase("b2"))
			doc.setPageSize(PageSize.B2);
		else if (pageSize.equalsIgnoreCase("b3"))
			doc.setPageSize(PageSize.B3);
		else if (pageSize.equalsIgnoreCase("b4"))
			doc.setPageSize(PageSize.B4);
		else if (pageSize.equalsIgnoreCase("b5"))
			doc.setPageSize(PageSize.B5);
		else if (pageSize.equalsIgnoreCase("b6"))
			doc.setPageSize(PageSize.B6);
		else if (pageSize.equalsIgnoreCase("b7"))
			doc.setPageSize(PageSize.B7);
		else if (pageSize.equalsIgnoreCase("b8"))
			doc.setPageSize(PageSize.B8);
		else if (pageSize.equalsIgnoreCase("b9"))
			doc.setPageSize(PageSize.B9);
		else if (pageSize.equalsIgnoreCase("b10"))
			doc.setPageSize(PageSize.B10);
		else if (pageSize.equalsIgnoreCase("arch_a"))
			doc.setPageSize(PageSize.ARCH_A);
		else if (pageSize.equalsIgnoreCase("arch_b"))
			doc.setPageSize(PageSize.ARCH_B);
		else if (pageSize.equalsIgnoreCase("arch_c"))
			doc.setPageSize(PageSize.ARCH_C);
		else if (pageSize.equalsIgnoreCase("arch_d"))
			doc.setPageSize(PageSize.ARCH_D);
		else if (pageSize.equalsIgnoreCase("arch_e"))
			doc.setPageSize(PageSize.ARCH_E);
		else if (pageSize.equalsIgnoreCase("flsa"))
			doc.setPageSize(PageSize.FLSA);
		else if (pageSize.equalsIgnoreCase("flse"))
			doc.setPageSize(PageSize.FLSE);
		else if (pageSize.equalsIgnoreCase("halfletter"))
			doc.setPageSize(PageSize.HALFLETTER);
		else if (pageSize.equalsIgnoreCase("_11x17"))
			doc.setPageSize(PageSize._11X17);
		else if (pageSize.equalsIgnoreCase("id_1"))
			doc.setPageSize(PageSize.ID_1);
		else if (pageSize.equalsIgnoreCase("id_2"))
			doc.setPageSize(PageSize.ID_2);
		else if (pageSize.equalsIgnoreCase("id_3"))
			doc.setPageSize(PageSize.ID_3);
		else if (pageSize.equalsIgnoreCase("ledger"))
			doc.setPageSize(PageSize.LEDGER);
		else if (pageSize.equalsIgnoreCase("crown_quarto"))
			doc.setPageSize(PageSize.CROWN_QUARTO);
		else if (pageSize.equalsIgnoreCase("large_crown_quarto"))
			doc.setPageSize(PageSize.LARGE_CROWN_QUARTO);
		else if (pageSize.equalsIgnoreCase("demy_quarto"))
			doc.setPageSize(PageSize.DEMY_QUARTO);
		else if (pageSize.equalsIgnoreCase("royal_quarto"))
			doc.setPageSize(PageSize.ROYAL_QUARTO);
		else if (pageSize.equalsIgnoreCase("crown_octavo"))
			doc.setPageSize(PageSize.CROWN_OCTAVO);
		else if (pageSize.equalsIgnoreCase("large_crown_octavo"))
			doc.setPageSize(PageSize.LARGE_CROWN_OCTAVO);
		else if (pageSize.equalsIgnoreCase("demy_octavo"))
			doc.setPageSize(PageSize.DEMY_OCTAVO);
		else if (pageSize.equalsIgnoreCase("royal_octavo"))
			doc.setPageSize(PageSize.ROYAL_OCTAVO);
		else if (pageSize.equalsIgnoreCase("small_paperback"))
			doc.setPageSize(PageSize.SMALL_PAPERBACK);
		else if (pageSize.equalsIgnoreCase("penguin_small_paperback"))
			doc.setPageSize(PageSize.PENGUIN_SMALL_PAPERBACK);
		else if (pageSize.equalsIgnoreCase("penguin_large_paperback"))
			doc.setPageSize(PageSize.PENGUIN_LARGE_PAPERBACK);
		else
			doc.setPageSize(PageSize.A4);
	}

}
