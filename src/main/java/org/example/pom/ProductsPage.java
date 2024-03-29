package org.example.pom;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import org.example.pom.bloks.ProductCard;

import java.util.List;
import java.util.NoSuchElementException;

@Getter
public class ProductsPage extends BasePage {

    Locator filter = pw.locator(".select_container");
    Locator productBlocks = pw.locator(".inventory_item");
    Locator blockTitle = pw.locator(".inventory_item_label");

    public ProductsPage(Page pw) {
        super(pw);
    }

    public String getFilterSelectedOption() {
        return filter.locator(".active_option").textContent();
    }

    public List<String> getFilterOptions() {
        return filter.locator("option").allTextContents();
    }

    public ProductCard getProductBlockByName(String name) {
        Locator cardLocator = productBlocks.filter(new Locator.FilterOptions().setHas(blockTitle).setHasText(name))
                .first();
        if (cardLocator != null) {
            return new ProductCard(cardLocator);
        } else {
            throw new NoSuchElementException("Product card with name '" + name + "' not found.");
        }
    }

    public void clickOnBlockTitleByIndex(int index) {
        blockTitle.getByRole(AriaRole.LINK).all().get(index).click();
    }
}
