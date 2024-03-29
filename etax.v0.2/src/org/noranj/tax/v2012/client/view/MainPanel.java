package org.noranj.tax.v2012.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MainPanel extends Composite {

  private static MainPanelUiBinder uiBinder = GWT.create(MainPanelUiBinder.class);

  @UiTemplate("MainPanel.ui.xml")
  interface MainPanelUiBinder extends UiBinder<Widget, MainPanel> {
  }


  public MainPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }


}
