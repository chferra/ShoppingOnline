/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import java.security.Principal;

/**
 *
 * @author Chris
 */
public class SimplePrincipal implements Principal {

        private final String email;
        private final boolean  negoziante;
        private final int id;

        public SimplePrincipal(String name, boolean negoziante, int id) {
            this.email = name;
            this.negoziante = negoziante;
            this.id = id;

        }

        @Override
        public String getName() {
            return email;
        }
        public boolean getNegoziante() {
            return negoziante;
        }
        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "SimplePrincipal{" +
                    "name='" + email + '\'' +
                    '}';
        }
    }
