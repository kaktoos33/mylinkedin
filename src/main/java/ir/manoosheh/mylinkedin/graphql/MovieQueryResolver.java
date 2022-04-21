//package ir.manoosheh.mylinkedin.graphql;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import ir.manoosheh.mylinkedin.model.Movie;
//import ir.manoosheh.mylinkedin.repository.MovieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class MovieQueryResolver implements GraphQLQueryResolver {
//
//    @Autowired
//    private MovieRepository movieRepository;
//
//    public Optional<Movie> movieById(Integer id) {
//        return movieRepository.findById(id);
//    }
//
//    public List<Movie> movies() {
//        return movieRepository.findAll();
//    }
//}
