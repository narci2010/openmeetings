/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openmeetings.test;

import static org.apache.openmeetings.db.util.ApplicationHelper.getWicketTester;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.List;

import org.apache.openmeetings.db.entity.user.User.Type;
import org.apache.openmeetings.util.OmException;
import org.apache.openmeetings.web.app.WebSession;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.tester.WicketTester;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.ButtonAjaxBehavior;

public class AbstractWicketTester extends AbstractJUnitDefaults {
	protected WicketTester tester;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		tester = getWicketTester();
		assertNotNull("Web session should not be null", WebSession.get());
	}

	public void login(String login, String password) {
		WebSession s = WebSession.get();
		try {
			if (login != null && password != null) {
				s.signIn(login, password, Type.user, null);
			} else {
				s.signIn(username, userpass, Type.user, null);
			}
		} catch (OmException e) {
			fail(e.getMessage());
		}
		assertTrue("Web session is not signed in for user: " + (login != null ? login : username), s.isSignedIn());
	}

	public <T extends Serializable> ButtonAjaxBehavior getButtonBehavior(String path, String name) {
		Args.notNull(path, "path");
		Args.notNull(name, "name");
		@SuppressWarnings("unchecked")
		AbstractDialog<T> dialog = (AbstractDialog<T>)tester.getComponentFromLastRenderedPage(path);
		List<ButtonAjaxBehavior> bl = dialog.getBehaviors(ButtonAjaxBehavior.class);
		for (ButtonAjaxBehavior bb : bl) {
			if (name.equals(bb.getButton().getName())) {
				return bb;
			}
		}
		return null;
	}
}
