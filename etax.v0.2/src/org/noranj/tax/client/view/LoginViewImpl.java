package org.noranj.tax.client.view;



import org.noranj.tax.client.presenter.LoginPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class LoginViewImpl<T> extends Composite implements LoginView<T> {
	  
	  @UiField PushButton googleButton;
	  
	  /* SA:12-AUG-19 Commented out
	  @ UiField PushButton twitterButton;
	  @ UiField PushButton facebookButton;
	  */
	  
	// This is used to hook the presenter to view
	  private Presenter<T> presenter;
		
	  private static UserLoginUiBinder uiBinder = GWT.create(UserLoginUiBinder.class);

	  @UiTemplate("LoginView.ui.xml")
	  interface UserLoginUiBinder extends UiBinder<Widget, LoginViewImpl> {	  }

	  public LoginViewImpl() {
	    initWidget(uiBinder.createAndBindUi(this));
	  }

	  @Override
	  public Widget asWidget() {
	    return this;
	  }

	  /* BA:12-03-01 Commented out
	  @Override
	  public HasClickHandlers getFacebookButton() {
	    return facebookButton;
	  }
	  @Override
	  public HasClickHandlers getTwitterButton() {
	    return twitterButton;
	  }
	  */
	  
	  @Override
	  public HasClickHandlers getGoogleButton() {
	    return googleButton;
	  }

	// This is used to hook the presenter to view
		@Override
		public void setPresenter(Presenter<T> presenter) {
			this.presenter = presenter;
		}


	}
