//package ir.manoosheh.mylinkedin.graphql;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import ir.manoosheh.mylinkedin.model.Movie;
//import ir.manoosheh.mylinkedin.payload.graphql.request.SignupRequest;
//import ir.manoosheh.mylinkedin.payload.graphql.response.SignupResponse;
//import ir.manoosheh.mylinkedin.repository.MovieRepository;
//import ir.manoosheh.mylinkedin.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MovieMutationResolver implements GraphQLMutationResolver {
//
//    @Autowired
//    private MovieRepository movieRepository;
//    @Autowired
//    final private UserService userService;
//
//    public MovieMutationResolver(UserService userService) {
//        this.userService = userService;
//    }
//
//    public Movie addMovie(Movie input) {
//        //Movie movie = new Movie(input.getId(), input.getName(), input.getDirector());
//        return movieRepository.save(input);
//    }
//
//    public SignupResponse signup(SignupRequest signupRequest) throws Exception /*throws IsExistException*/ {
//        return userService.save(signupRequest);
//
//    }
//}
//
