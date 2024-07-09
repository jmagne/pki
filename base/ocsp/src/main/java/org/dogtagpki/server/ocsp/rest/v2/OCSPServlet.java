//
// Copyright Red Hat, Inc.
//
// SPDX-License-Identifier: GPL-2.0-or-later
//
package org.dogtagpki.server.ocsp.rest.v2;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dogtagpki.server.ocsp.OCSPEngine;
import org.dogtagpki.server.rest.v2.PKIServlet;

/**
 * @author Marco Fargetta {@literal <mfargett@redhat.com>}
 */
public class OCSPServlet extends PKIServlet {
    public static final long serialVersionUID = 1L;

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    public void patch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    public OCSPEngine getOCSPEngine() {
        ServletContext servletContext = getServletContext();
        return (OCSPEngine) servletContext.getAttribute("engine");
    }

    @Override
    protected String getSubsystemName() {
        return getOCSPEngine().getID();
    }
}