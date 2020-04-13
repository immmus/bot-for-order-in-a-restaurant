package com.immmus.telegram.bot.service;

import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.domain.menu.CommonPosition;
import com.immmus.infrastructure.api.domain.menu.MenuContext;
import com.immmus.infrastructure.api.service.ContextService;
import com.immmus.infrastructure.api.service.MenuContextService;
import com.immmus.telegram.bot.repository.MenuPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService implements MenuPositionRepository {
    //TODO работа с триггерами базы для возможного обновления меню https://evileg.com/ru/post/372/
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<CommonPosition> positionRowMapper =
            (rs, rowNum) -> {
                CommonPosition pos = CommonPosition.builder()
                        .name(rs.getString("name"))
                        .price(rs.getDouble("price"))
                        .composition(rs.getString("composition"))
                        .category(Position.Category.valueOf(rs.getString("category")))
                        .description(rs.getString("description"))
                        .activate(rs.getBoolean("isActive"))
                        .create();
                pos.setId(rs.getInt("id"));
                return pos;
            };

    @Autowired
    public MenuService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MenuContextService getMenuContextService() {
        final MenuContext menuContext = new MenuContext(getMenu());
        return ContextService.create(menuContext, MenuContextService.class);
    }

    @Override
    public List<CommonPosition> getMenu() {
     return getAllPosition()
             .stream()
             .filter(CommonPosition::isActive)
             .collect(Collectors.toList());
    }

    private List<CommonPosition> getAllPosition() {
        return jdbcTemplate.query("SELECT * FROM menu_positions", this.positionRowMapper);
    }
}