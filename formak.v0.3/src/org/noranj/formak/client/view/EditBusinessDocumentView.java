package org.noranj.formak.client.view;

import org.noranj.formak.client.presenter.EditBusinessDocumentPresenter;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * renamed the attributes. see the comment in below
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class EditBusinessDocumentView extends Composite implements EditBusinessDocumentPresenter.Display {

  //BA-2012-JAN-28 Added it was not in Contact
  private final TextBox id; // firstName
  private final TextBox businessDocumentName; // firstName
  private final TextBox description; //lastName
  private final TextBox businessDocumentNumber; //emailAddress
  private final FlexTable detailsTable;
  private final Button saveButton;
  private final Button cancelButton;
  
  public EditBusinessDocumentView() {
    DecoratorPanel contentDetailsDecorator = new DecoratorPanel();
    contentDetailsDecorator.setWidth("18em");
    initWidget(contentDetailsDecorator);

    VerticalPanel contentDetailsPanel = new VerticalPanel();
    contentDetailsPanel.setWidth("100%");

    // Create the contacts list
    //
    detailsTable = new FlexTable();
    detailsTable.setCellSpacing(0);
    detailsTable.setWidth("100%");
    detailsTable.addStyleName("businessDocuments-ListContainer");
    detailsTable.getColumnFormatter().addStyleName(1, "add-businessDocument-input");
    id = new TextBox(); //BA-2012-JAN-28 Added
    businessDocumentName = new TextBox();
    description = new TextBox();
    businessDocumentNumber = new TextBox();
    initDetailsTable();
    contentDetailsPanel.add(detailsTable);
    
    HorizontalPanel menuPanel = new HorizontalPanel();
    saveButton = new Button("Save");
    cancelButton = new Button("Cancel");
    menuPanel.add(saveButton);
    menuPanel.add(cancelButton);
    contentDetailsPanel.add(menuPanel);
    contentDetailsDecorator.add(contentDetailsPanel);
  }
  
  private void initDetailsTable() {
    
    detailsTable.setWidget(0, 0, new Label("BizDoc Name")); // Firstname
    detailsTable.setWidget(0, 1, businessDocumentName);
    detailsTable.setWidget(1, 0, new Label("Description")); // Lastname
    detailsTable.setWidget(1, 1, description);
    detailsTable.setWidget(2, 0, new Label("BizDoc Number")); // Email Address
    detailsTable.setWidget(2, 1, businessDocumentNumber);
    detailsTable.setWidget(3, 0, new Label("BizDoc Id")); //BA-2012-JAN-28 Added
    detailsTable.setWidget(3, 1, id); //BA-2012-JAN-28 Added
    businessDocumentName.setFocus(true);
  }

  public HasValue<String> getId() {
    return id;
  }
  
  public HasValue<String> getBusinessDocumentName() {
    return businessDocumentName;
  }

  public HasValue<String> getDescription() {
    return description;
  }

  public HasValue<String> getBusinessDocumentNumber() {
    return businessDocumentNumber;
  }

  public HasClickHandlers getSaveButton() {
    return saveButton;
  }
  
  public HasClickHandlers getCancelButton() {
    return cancelButton;
  }
  
  public Widget asWidget() {
    return this;
  }
}
