package com.immmus.telegram.bot.service;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.repository.MenuPosition;
import com.immmus.telegram.bot.dto.TMenu;
import com.immmus.telegram.bot.repository.MenuPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService implements MenuPositionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Position> positionRowMapper =
            (rs, rowNum) -> {
                var pos = MenuPosition.builder()
                        .name(rs.getString("name"))
                        .price(rs.getDouble("price"))
                        .composition(rs.getString("composition"))
                        .category(Position.Category.valueOf(rs.getString("category")))
                        .description(rs.getString("description"))
                        .create();
                pos.setId(rs.getInt("id"));
                return pos;
            };

    @Autowired
    public MenuService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Menu<Position> getMenu() {
        List<Position> allPosition = getAllPosition();
        var currentMenPositionIds = new HashSet<>(jdbcTemplate.queryForList("SELECT * FROM MENU", Integer.TYPE));
        return new TMenu(allPosition.stream()
                .filter(pos -> currentMenPositionIds.contains(pos.getId()))
                .collect(Collectors.toList()));
    }

    private List<Position> getAllPosition() {
        return jdbcTemplate.query("SELECT * FROM menu_position", this.positionRowMapper);
    }
}
