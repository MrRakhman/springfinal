package kz.bitlab.techboot.springfinal.controller;

import jakarta.transaction.Transactional;
import kz.bitlab.techboot.springfinal.model.Blog;
import kz.bitlab.techboot.springfinal.model.Olympiads;
import kz.bitlab.techboot.springfinal.model.Problem;
import kz.bitlab.techboot.springfinal.model.User;
import kz.bitlab.techboot.springfinal.service.BlogService;
import kz.bitlab.techboot.springfinal.service.OlympiadService;
import kz.bitlab.techboot.springfinal.service.ProblemService;
import kz.bitlab.techboot.springfinal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BlogService blogService;
    private final UserService userService;
    private final OlympiadService olympiadService;
    private final ProblemService problemService;

    @GetMapping(value = "/")
    public String indexPage(Model model){
        User user = userService.getCurrentSessionUser();
        model.addAttribute("blogs", blogService.getBlogsList());
        model.addAttribute("User", user);
        return "index";
    }

    @GetMapping(value = "/add-blog-page")
    public String addBlogPage(){
        return "add_blog_page";
    }

    @PostMapping(value = "/add-blog")
    public String addBlog(@RequestParam(name = "blog_title") String blogTitle,
                          @RequestParam(name = "blog_content") String blogContent){
        User user = userService.getCurrentSessionUser();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy, h:mm a");
        String formattedDate = now.format(formatter);

        Blog blog = new Blog();
        blog.setAuthor(user);
        blog.setTitle(blogTitle);
        blog.setPostDate(formattedDate);
        blog.setContent(blogContent);
        blogService.addBlog(blog);
        return "redirect:/";
    }

    @GetMapping(value = "/edit-blog-page/{blogId}")
    public String editBlogPage(@PathVariable(name = "blogId") Long blogId, Model model){
        Blog blog = blogService.getBlog(blogId);
        model.addAttribute("Blog", blog);
        return "edit_blog_page";
    }

    @PostMapping(value = "/edit-blog")
    public String editBlog(@RequestParam(name = "blogId") Long blogId,
                           @RequestParam(name = "blog_title") String blogTitle,
                           @RequestParam(name = "blog_content") String blogContent){
        Blog blog = blogService.getBlog(blogId);
        blog.setAuthor(blog.getAuthor());
        blog.setTitle(blogTitle);
        blog.setContent(blogContent);
        blog.setPostDate(blog.getPostDate());
        blogService.addBlog(blog);
        return "redirect:/";
    }

    @GetMapping(value = "/register-page")
    public String registerPage(Model model){
        String[] countryCodes = Locale.getISOCountries();
        List<String> countriesList = new ArrayList<>();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            countriesList.add(locale.getDisplayCountry());
        }
        model.addAttribute("countriesList", countriesList);
        return "register";
    }

    @PostMapping(value = "/register")
    public String toSignUp(@RequestParam(name = "user_login") String login,
                           @RequestParam(name = "full_name") String fullName,
                           @RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "user_repeat_password") String repeatPassword,
                           @RequestParam(name = "country") String country,
                           @RequestParam(name = "occupation") String occupation){
        if (password.equals(repeatPassword)){
            User user = User.builder()
                    .login(login)
                    .fullName(fullName)
                    .email(email)
                    .password(password)
                    .occupation(occupation).country(country).build();
            User newUser = userService.addUser(user);
            if (newUser != null){
                return "redirect:/register-page?success";
            }else{
                return "redirect:/register-page?emailerror";
            }
        }
        else{
            return "redirect:/register-page?passworderror";
        }
    }

    @GetMapping(value = "/sign-in-page")
    public String signInPage(){
        return "signin";
    }

    @GetMapping(value = "/my-page")
    public String myPage(Model model){
        model.addAttribute("User", userService.getCurrentSessionUser());
        return "my_account";
    }

    @GetMapping(value = "/edit-account-page")
    public String editAccountPage(Model model){
        String[] countryCodes = Locale.getISOCountries();
        List<String> countriesList = new ArrayList<>();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("english", countryCode);
            countriesList.add(locale.getDisplayCountry());
        }
        model.addAttribute("countriesList", countriesList);

        User user = userService.getCurrentSessionUser();
        model.addAttribute("user", user);
        return "edit_account";
    }

    @PostMapping(value = "/edit-account")
    public String editAccount(@RequestParam(name = "full_name") String fullName,
                              @RequestParam(name = "user_login") String userLogin,
                              @RequestParam(name = "user_email") String userEmail,
                              @RequestParam(name = "user_occupation") String userOccupation,
                              @RequestParam(name = "user_country") String userCountry){
        User user = userService.getCurrentSessionUser();
        user.setFullName(fullName);
        user.setLogin(userLogin);
        user.setCountry(userCountry);
        user.setEmail(userEmail);
        user.setPassword(user.getPassword());
        user.setOccupation(userOccupation);
        userService.saveUser(user);
        return "redirect:/my-page";
    }

    @GetMapping(value = "/profile")
    public String profilePage(){
        return "profile";
    }

    @GetMapping(value = "/users-page")
    public String usersPage(Model model){
        model.addAttribute("users", userService.getUsersList());
        return "users";
    }

    @GetMapping(value = "/specific_user_page/{userId}")
    public String SpecificUsersPage(@PathVariable(name = "userId") Long id, Model model){
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "specific_user";
    }

    @GetMapping(value = "/olympiads-page")
    public String olympiadsPage(Model model){
//        model.addAttribute("User", userService.getCurrentSessionUser());
        model.addAttribute("olympiads", olympiadService.getOlympiadsList());
        return "olympiads";
    }

    @PostMapping(value = "/add-olympiad")
    public String addOlympiad(@RequestParam(name = "olympiad_title") String olympiadTitle,
                              @RequestParam(name = "olympiad_description") String olympiadDescription,
                              @RequestParam(name = "limitation") String limitation){
        Olympiads olympiad = new Olympiads();
        User fullName = userService.getCurrentSessionUser();

        olympiad.setAuthor(fullName);
        olympiad.setTitle(olympiadTitle);
        olympiad.setDescription(olympiadDescription);
        olympiad.setLimitation(limitation);
        olympiadService.addOlympiad(olympiad);
        return "redirect:/olympiads-page?success";
    }

    @GetMapping(value = "/problems-page")
    public String problemsPage(Model model){
        User user = userService.getCurrentSessionUser();
        model.addAttribute("problems", problemService.getProblemsList());
        model.addAttribute("User", user);
        return "problems";
    }

    @PostMapping(value = "/add-problem")
    public String addProblem(@RequestParam(name = "problem_name") String problemName,
                             @RequestParam(name = "problem_difficulty") String problemDifficulty,
                             @RequestParam(name = "problem_tags") String problemTags,
                             @RequestParam(name = "problem_description") String problemDescription,
                             @RequestParam(name = "problem_answer") String problemAnswer){
        Problem problem = new Problem();
        User author = userService.getCurrentSessionUser();
        problem.setAuthor(author);
        problem.setName(problemName);
        problem.setDifficulty(problemDifficulty);
        problem.setTags(problemTags);
        problem.setDescription(problemDescription);
        problem.setAnswer(problemAnswer);
        problem.setDescription(problem.getDescription());
        problemService.addProblem(problem);
        return "redirect:/problems-page?success";
    }

    @GetMapping(value = "/problem/{problemId}")
    public String problemDescriptionPage(@PathVariable(name = "problemId") Long id, Model model){
        User user = userService.getCurrentSessionUser();
        Problem problem = problemService.getProblem(id);

        if (problem.getSolvedUsers().contains(user)){
            return "solved_problem";
        }
        model.addAttribute("problem", problem);
        return "problem_description";
    }

    @Transactional
    @PostMapping(value = "/submit-answer")
    public String submitAnswer(@RequestParam(name = "problemId") Long problemId,
                               @RequestParam(name = "users_answer") String usersAnswer){
        User user = userService.getCurrentSessionUser();
        Problem problem = problemService.getProblem(problemId);
        List<User> solvedUsers = problem.getSolvedUsers();

        if (problem !=null){
            if (!solvedUsers.contains(user)){
                String realAnswer = problem.getAnswer();
                if (realAnswer != null){
                    if (realAnswer.equals(usersAnswer)){
                        user.setProblemRating(user.getProblemRating()+1);
                        problem.addSolverUser(user);
                        problem.setSolvedUsersCount(problem.solverUsersSize());
                        problemService.saveProblem(problem);
                        userService.saveUser(user);

                        for (User prob: problem.getSolvedUsers()){
                            System.out.println(prob);
                        }

                        return "redirect:/problem/" + problemId + "?success";
                    }else{
                        return "redirect:/problem/" + problemId + "?failure";
                    }
                }else{
                    return "redirect:/problem/" + problemId + "?nocorrectanswer";
                }
            }else{
                return "redirect:/problem/" + problemId + "?solved";
            }
        }
        return "solved_problem";
    }

    @GetMapping(value = "/olympiad/{olympiadId}")
    public String openOlympiadPage(@PathVariable(name = "olympiadId") Long id, Model model){
        Olympiads olympiad = olympiadService.getOlympiad(id);
        model.addAttribute("olympiad", olympiad);
        return "specific_olympiad";
    }

    @GetMapping(value = "/update-password-page")
    public String updatePasswordPage(){
        return "update_password";
    }

    @PostMapping(value = "/update-password")
    public String updatePassword(
            @RequestParam(name = "old_password") String oldPassword,
            @RequestParam(name = "new_password") String newPassword,
            @RequestParam(name = "repeat_new_password") String repeatPassword){

        if (newPassword.equals(repeatPassword)){
            User user = userService.updatePassword(newPassword, oldPassword);
            if (user != null){
                return "redirect:/update-password-page?success";
            }else{
                return "redirect:/update-password-page?oldpassworderror";
            }
        }else{
            return "redirect:/update-password-page?passwordismatch";
        }
    }

    @GetMapping(value = "/not-registered-page")
    public String notRegisteredPage(){
        return "empty_page";
    }
}
