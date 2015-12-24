package yporque.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yporque.config.MemoryDBConfig;
import yporque.model.Item;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by francisco on 04/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("yporque.model")
@ContextConfiguration(classes = {MemoryDBConfig.class})
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @After
    public void tearDown() throws Exception {
        itemRepository.deleteAll();
    }

    @Test
    public void test_insert_item() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item("1",1, 1L));
        items.add(new Item("2",1, 1L));
        items.add(new Item("3",1, 1L));

        itemRepository.save(items);

        List<Item> items1 = itemRepository.findAll();
        Assert.assertThat(items1,hasSize(3));
        Assert.assertThat(items1.get(0).getCantidad(),is(items.get(0).getCantidad()));
        Assert.assertThat(items1.get(0).getCodigo(),is(items.get(0).getCodigo()));

    }

    @Test
    public void test_update_item() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item("1", 1, 1L));
        items.add(new Item("2", 1, 1L));
        items.add(new Item("3", 1, 1L));

        itemRepository.save(items);

        List<Item> items1 = itemRepository.findAll();
        Item item = items1.get(0);
        item.setVentaId(1L);
        item.setCantidad(2);
        item.setCodigo("33");
        itemRepository.save(item);
        Item item2 = itemRepository.findByCodigo("33").get(0);
        Assert.assertThat(item2.getCantidad(),is(2));
        Assert.assertThat(item2.getCodigo(),is("33"));
        Assert.assertThat(item2.getVentaId(),is(1L));
        Assert.assertThat(item2.getArticuloId(),is(1L));

    }

    @Test
    public void test_findByCodigo() throws Exception {

        Item item = new Item("22",1, 1L);
        itemRepository.save(item);
        Item item1 = itemRepository.findByCodigo("22").get(0);

        Assert.assertThat(item1.getCodigo(),is("22"));
        Assert.assertThat(item1.getCantidad(),is(1));

    }


    @Test
    public void test_findByArticuloId_pageable() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item("1",1, 1L));
        items.add(new Item("2",1, 1L));
        items.add(new Item("3",1, 1L));
        itemRepository.save(items);

        Page<Item> page = itemRepository.findByArticuloId(1L,new PageRequest(0,1));

        Assert.assertThat(page.getTotalElements(),is(3L));
        Assert.assertThat(page.getTotalPages(),is(3));
        Assert.assertThat(page.isFirst(),is(true));
        Assert.assertThat(page.getContent().get(0).getCodigo(),is("1"));

    }

    @Test
    public void test_findByArticuloId() throws Exception {
        List<Item> items = new ArrayList<>();
        items.add(new Item("1",1, 1L));
        items.add(new Item("2",1, 1L));
        items.add(new Item("3",1, 1L));
        itemRepository.save(items);

        List<Item> list = itemRepository.findByArticuloId(1L);

        Assert.assertThat(list,hasSize(3));
        Assert.assertThat(list.get(0).getCodigo(),is("1"));

    }

}
