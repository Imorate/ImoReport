package ir.imorate.imoreport;

import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@Controller
@AllArgsConstructor
public class HomeController {

    private final Faker faker;
    private final ResourceLoader resourceLoader;

    @RequestMapping("/")
    public String home(Model model) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            employees.add(new Employee(
                    String.format("%s %s", faker.name().firstName(), faker.name().lastName()),
                    faker.country().name(),
                    faker.date().birthday(),
                    new BigDecimal((new Random().nextInt(10000) + 500)),
                    new BigDecimal((new Random().nextInt(10000) + 500)))
            );
        }
        model.addAttribute("employees", employees);
        try (InputStream is = Objects.requireNonNull(resourceLoader.getClassLoader()).getResourceAsStream("template.xlsx")) {
            try (OutputStream os = new FileOutputStream("./report/Report.xlsx")) {
                Context context = new Context();
                context.putVar("employees", employees);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "home";
    }

}