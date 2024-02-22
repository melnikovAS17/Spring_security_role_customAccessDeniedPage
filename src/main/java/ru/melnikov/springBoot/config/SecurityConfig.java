package ru.melnikov.springBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//В новых версиях Security класс WebConfigurationAdapter - deprecated
//Нужно конфигурировать с помощью SecurityFilterChain
@Configuration
@EnableWebSecurity
public class SecurityConfig  {//Spring сам вытаскивает данные из бд, нужно только релизовать
                              //репозиторий и сервис расширив нужные интерфейсы

    //Настройка Spring Security и авторизации
    //В новой версии Security конфиг пишется в SecurityFilterChain
    //И для блоков авторизации, аутен-ции, форм и тп используются lambda
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return  http
                //CSRF включен по умолчанию
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
                                .failureUrl("/auth?error")
                )
                //Авторизация
                .authorizeHttpRequests(auth ->
                        auth
                                //Даёт доступ всем пользователям(авториз-ым и нет) к адресу "/auth"
                                .requestMatchers("/auth","/auth/registration").permitAll()
                                .anyRequest().authenticated()
                                //Метод - anyRequest() необязателен, можно указать только те
                                //ссылки которые должны быть доступны в методе requestMatchers()
                                //но тогда у зарегистр-ых юзеров не будет прав доступа
                                //так пока роли им не присвоены
                )
                //Блок разлогинивания(удалит юзера из сессии и куки у юзера)
                .logout(out ->out
                        //Данный url нужно просто указать в каком-то html отображении (например в качестве ссылки)
                        .logoutUrl("/logout")
                        //В случае успешного разлогинивания первёд на url - /auth
                        .logoutSuccessUrl("/auth"))
                .build();

    }
    //Устанавливаем кодировку
    //Проверку на валидность пароля spring делает сам, те нам не надо добавлять никаие компоненты
    //для проверки (есть ли такой пароль в базе или нет)
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
