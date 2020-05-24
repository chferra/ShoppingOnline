package authentication;

import io.jsonwebtoken.Claims;
import java.net.Authenticator;
import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.Optional;


@Provider
@Authenticated
@Priority(Priorities.AUTHENTICATION)

public class AuthenticationFilter implements ContainerRequestFilter {

    public final String authorizationHeaderSchema;
    public final String authenticationCookieName;

    public AuthenticationFilter() {
        this.authorizationHeaderSchema = "Bearer";
        this.authenticationCookieName = "panDiStelle";
        System.out.println("okay");
        //throw new Exception("ok");
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext) {
        
        final Optional<Claims> claims = hasAuthorizationHeader(requestContext)
                .map(Optional::of)
                .orElseGet(() -> hasSessionCookie(requestContext));
        if(!claims.isPresent())
            throw new NotAuthorizedException("A valid authorization header or cookie must be provided");

        final SimplePrincipal simplePrincipal = new SimplePrincipal(claims.get().getSubject(), claims.get().get("negoziante", Boolean.class), claims.get().get("id", Integer.class));
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return simplePrincipal;
            }

            @Override
            public boolean isUserInRole(String role) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        });
    }

    protected Optional<Claims> hasAuthorizationHeader(ContainerRequestContext requestContext) {
        final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null || !authorizationHeader.startsWith(authorizationHeaderSchema + " "))
            return Optional.empty();

        final String token = authorizationHeader.substring(authorizationHeaderSchema.length()).trim();
        try {
            return Optional.ofNullable(JwtAuthenticationService.getInstance().authenticate(token));
        } catch (AuthenticationException e) {
            return Optional.empty();
        }
    }

    protected Optional<Claims> hasSessionCookie(ContainerRequestContext requestContext) {
        final Cookie cookie = requestContext.getCookies().get(authenticationCookieName);
        if(cookie == null)
            return Optional.empty();

        final String token = cookie.getValue();
        try {
            return Optional.ofNullable(JwtAuthenticationService.getInstance().authenticate(token));
        } catch (AuthenticationException e) {
            return Optional.empty();
        }
    }

    
}
