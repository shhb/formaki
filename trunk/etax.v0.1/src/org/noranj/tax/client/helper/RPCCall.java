package org.noranj.tax.client.helper;

import org.noranj.tax.client.Tax;
import org.noranj.tax.shared.exception.NotLoggedInException;
import org.noranj.tax.client.event.LogoutEvent;
import org.noranj.tax.client.event.RPCInEvent;
import org.noranj.tax.client.event.RPCOutEvent;
import org.noranj.tax.shared.Constants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;

/**
 * 
 * @version 0.3.2012MAR01
 * @since 0.3.2012MAR01
 * @param <T>
 * @change
 *  
 */
public abstract class RPCCall<T> implements AsyncCallback<T> {

  protected abstract void callService(AsyncCallback<T> cb);

  private void call(final int retriesLeft) {
    onRPCOut();

    callService(new AsyncCallback<T>() {
      public void onFailure(Throwable caught) {
        onRPCIn();
        GWT.log(caught.toString(), caught);
        try {
          throw caught;
        } catch (InvocationException invocationException) {
          if(caught.getMessage().equals(Constants.C_LOGGED_OUT)){
                //BA:12-MAR-01
                //ConnectrApp.get().getEventBus().fireEvent(new LogoutEvent());
                Tax.get().getEventBus().fireEvent(new LogoutEvent());
                return;
          }
          
          if (retriesLeft <= 0) {
            RPCCall.this.onFailure(invocationException);
          } else {
            call(retriesLeft - 1); // retry call
          }
        } catch (IncompatibleRemoteServiceException remoteServiceException) {
          Window.alert("The app maybe out of date. Reload this page in your browser.");
        } catch (SerializationException serializationException) {
          Window.alert("A serialization error occurred. Try again.");
        } catch (NotLoggedInException e) {
          //BA:2012-MAR-01
          //ConnectrApp.get().getEventBus().fireEvent(new LogoutEvent());
          Tax.get().getEventBus().fireEvent(new LogoutEvent());
        } catch (RequestTimeoutException e) {
          Window.alert("This is taking too long, try again");
        } catch (Throwable e) {// application exception
          RPCCall.this.onFailure(e);
        }
      }

      public void onSuccess(T result) {
        onRPCIn();
        RPCCall.this.onSuccess(result);
      }
    });
  }

  private void onRPCIn() {
    Tax.get().getEventBus().fireEvent(new RPCInEvent());
  }

  private void onRPCOut() {
    Tax.get().getEventBus().fireEvent(new RPCOutEvent());
  }

  public void retry(int retryCount) {
    call(retryCount);
  }
}
