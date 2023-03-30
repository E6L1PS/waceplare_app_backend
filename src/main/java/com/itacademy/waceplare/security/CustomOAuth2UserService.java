package com.itacademy.waceplare.security;

/*
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Получаем OAuth2AccessToken из запроса
        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        // Получаем Map с данными пользователя из OAuth2-провайдера
        Map<String, Object> userAttributes = super.loadUser(userRequest).getAttributes();

        // Получаем email пользователя из Map
        String email = (String) userAttributes.get("email");

        // Ищем пользователя с таким email в базе данных
        UserCredentials user = userService.findByEmail(email);

        // Если пользователь не найден, создаем нового пользователя с ролью "ROLE_USER"
        if (user == null) {
            user = new UserCredentials();
            user.setEmail(email);
            user.setRoles(Collections.singleton(new Role("USER")));
            userService.save(user);
        }

        // Создаем объект CustomOAuth2User с информацией о пользователе и его ролях
        return new CustomOAuth2User(user, userAttributes);
    }
}
*/
