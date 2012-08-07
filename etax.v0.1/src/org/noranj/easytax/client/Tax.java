/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noranj.easytax.client;

import org.noranj.easytax.client.Tax.TaxUiBinder;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tax implements EntryPoint {
	
	private static final TaxUiBinder uiBinder = GWT.create(TaxUiBinder.class);
	
	interface TaxUiBinder extends UiBinder<DockLayoutPanel, Tax> {	}
	
	RootLayoutPanel root;
	
	
	private static Tax singleton;
	
	public static Tax get() {
	    return singleton;
	  }
	public void onModuleLoad() {
		singleton = this;
		DockLayoutPanel outer = uiBinder.createAndBindUi(this);
		root = RootLayoutPanel.get();
		root.clear();
		root.add(outer);
	}
}
