package ru.melnikov.springBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//В новых версиях Security класс WebConfigurationAdapter - depricated
//Нужно конфигурировать с помощью SecurityFilterChain
@Configuration
@EnableWebSecurity
public class SecurityConfig  {//Spring сам вытаскивает данные из бд, нужно тольео релизовать
                                //репозиторий и сервис расширив нужные интерфейсы

    //Настройка Spring Security и авторизации
    //В новой версии Security конфиг пишется в SecurityFilterChain
    //И для блоков авторизации, аутен-ции, форм и тп используются lambda
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return  http
                //Отключение csrf
                .csrf(AbstractHttpConfigurer::disable)
                //Описание формы регистрации
                .formLogin(form ->
                        //Адресс формы - по мапингу контроллера
                        form.loginPage("/auth")
                                //Означает, что обрабатывать данные с формы будем в Spring Security
                                //можно указать любой путь, сделано для того чтобы не осздавать
                                //Post метод в контроддере для обработки данных с формы
                                .loginProcessingUrl("/process_login")
                                //В случае успешной аутентификации spring перекинет на метод
                                //контроллера с мапингом на "/hello"
                                .defaultSuccessUrl("/hello", true)
                                //В случае неудачи будет редирект на ту же форму с указанием ошибки
                                .failureUrl("/auth?error"))
                //Авторизация
                .authorizeHttpRequests(auth ->
                        auth
                                //Даёт доступ всем пользователям(авториз-ым и нет) к адресу "/auth"
                                .requestMatchers("/auth").permitAll()
                                //Остальные запросы только для авториз-ых пользователей
                                .anyRequest().authenticated())//Авторизация
                .build();

    }
    //Тк пароль не шифруем использую NoPasswordEncoder
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
