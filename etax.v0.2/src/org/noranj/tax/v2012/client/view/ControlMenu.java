package org.noranj.tax.v2012.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ControlMenu<T> extends Composite implements IControlMenu<T> {

	private static ControlMenuUiBinder uiBinder = GWT.create(ControlMenuUiBinder.class);

	@UiTemplate("ControlMenu.ui.xml")
	interface ControlMenuUiBinder extends UiBinder<Widget, ControlMenu> {	}

	@UiField
	StackLayoutPanel menuRoot;
	
	public ControlMenu() {
		initWidget(uiBinder.createAndBindUi(this));
		 Widget header = createHeaderWidget("Educational");
		 Widget filter = createHeaderWidget("Properties");
		 Widget footer = createHeaderWidget("Contracts");
		 menuRoot.add(createMailItem(), header,2);
		 menuRoot.add(createFiltersItem(), filter,2);
		 menuRoot.add(createMailItem(), footer,2);
		//menuRoot.add(new HTML("this content"), new HTML("this"), 4);

	}
	  private void addItem(TreeItem root, String label) {
		    SafeHtmlBuilder sb = new SafeHtmlBuilder();
		    
		    //sb.append(SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(image).getHTML()));
		    sb.appendEscaped(" ").appendEscaped(label);
		    root.addItem(sb.toSafeHtml());
		  }
	 private Widget createMailItem() {
		 	Tree mailPanel = new Tree();
		    TreeItem mailPanelRoot = mailPanel.addItem("Group 1");
		    addItem(mailPanelRoot, "1");
		    addItem(mailPanelRoot, "2");
		    addItem(mailPanelRoot, "3");
		    addItem(mailPanelRoot, "4");
		    addItem(mailPanelRoot, "5");
		    mailPanelRoot.setState(true);
		    return mailPanel;
		  }

	 private Widget createFiltersItem() {
		    VerticalPanel filtersPanel = new VerticalPanel();
		    filtersPanel.setSpacing(4);
		    CheckBox checkBox = new CheckBox("Enter age");
		    checkBox.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Window.alert(event.getSource().toString());	
				}
			});
		    filtersPanel.add(checkBox);
		    filtersPanel.add(new CheckBox("Enter Salary"));
		    filtersPanel.add(new CheckBox("Morgage Items"));
		    return new SimplePanel(filtersPanel);
		  }
	 private Widget createHeaderWidget(String text){
		    // Add the image and text to a horizontal panel
		    HorizontalPanel hPanel = new HorizontalPanel();
		    hPanel.setHeight("100%");
		    hPanel.setSpacing(0);
		    hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		    //hPanel.add(new Label("Hi"));
		    HTML headerText = new HTML(text);
		    //headerText.setStyleName("cw-StackPanelHeader");
		    hPanel.add(headerText);
		    return new SimplePanel(hPanel);
		  }

	 
	@Override
	public void setPresenter(
			org.noranj.tax.v2012.client.view.IControlMenu.Presenter<T> presenter) {
		// TODO Auto-generated method stub
		
	}
	
	
}
