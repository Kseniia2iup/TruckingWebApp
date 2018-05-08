package ru.tsystems.javaschool.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.tsystems.javaschool.model.UserProfile;
import ru.tsystems.javaschool.service.UserProfileService;


@Component
public class RoleToUserProfileConverter implements Converter<Object, UserProfile>{
    private static final Logger LOG = LoggerFactory.getLogger(RoleToUserProfileConverter.class);

    @Autowired
    UserProfileService userProfileService;

    /*
     * Gets UserProfile by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public UserProfile convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        UserProfile profile= userProfileService.findById(id);
        LOG.info("From RoleToUserProfileConverter convert method" +
                "\nProfile : "+profile);
        return profile;
    }

    /*
     * Gets UserProfile by type
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    /*
    public UserProfile convert(Object element) {
        String type = (String)element;
        UserProfile profile= userProfileService.findByType(type);
        System.out.println("Profile ... : "+profile);
        return profile;
    }
    */
}
