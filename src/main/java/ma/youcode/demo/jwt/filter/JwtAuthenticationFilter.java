package ma.youcode.demo.jwt.filter;

import ma.youcode.demo.jwt.services.CustomUserDetailService;
import ma.youcode.demo.jwt.util.JwtUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
// OncePerRequestFilter for a single request this filter will be called only one
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
// we should get the jwt token from the request heder !
//         after that we should validate that jwt token
//        comming from the httpRequest header we store it in bearertoken variable
        String bearerToken = request.getHeader("Authorization");
//        storing in variable the username and the token
        String username = null;
        String token = null;

//check if is the token exists or not
        if (bearerToken != null && bearerToken.startsWith("Bearer")){
//            extracting the token from the bearer token
            token = bearerToken.substring(7);
            try {
//                extract the username from the token
                username = jwtUtil.extractUsername(token);
//                get user details for this user
               UserDetails userDetails =  customUserDetailService.loadUserByUsername(username);
//              secutiry check
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(
                                    (request)
                            ));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);




                } else {
                    System.out.println("Invalid token");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("Invalid Bearer token format !");
        }
//        if it's okay it wil forward the filter request to the requested endpoint

        filterChain.doFilter(request, response);
    }
}
