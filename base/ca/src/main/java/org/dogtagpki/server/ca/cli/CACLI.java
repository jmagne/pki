// --- BEGIN COPYRIGHT BLOCK ---
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; version 2 of the License.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//
// (C) 2019 Red Hat, Inc.
// All rights reserved.
// --- END COPYRIGHT BLOCK ---

package org.dogtagpki.server.ca.cli;

import org.dogtagpki.cli.CLI;
import org.dogtagpki.server.cli.SDCLI;
import org.dogtagpki.server.cli.SubsystemGroupCLI;
import org.dogtagpki.server.cli.SubsystemUserCLI;

/**
 * @author Endi S. Dewata
 */
public class CACLI extends CLI {

    public CACLI(CLI parent) {
        super("ca", "CA subsystem management commands", parent);

        addModule(new CACertCLI(this));
        addModule(new CACRLCLI(this));
        addModule(new CADBCLI(this));
        addModule(new SubsystemGroupCLI(this));
        addModule(new CAProfileCLI(this));
        addModule(new CARangeCLI(this));
        addModule(new CAIdCLI(this));
        addModule(new SubsystemUserCLI(this));
        addModule(new SDCLI(this));
    }
}
