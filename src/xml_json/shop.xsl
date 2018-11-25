<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>My Shop</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Категория</th>
                        <th>Подкатегория</th>
                        <th>Производитель</th>
                        <th>Модель</th>
                        <th>Цвет</th>
                        <th>Дата производства</th>
                        <th>Цена</th>
                        <th>Количество</th>
                    </tr>
                    <xsl:for-each select="shop/category">
                            <xsl:for-each select="subcategory">
                                <xsl:for-each select="product">
                                    <tr>
                                        <td><xsl:value-of select="../../categoryName"/></td>
                                        <td><xsl:value-of select="../subcategoryName"/></td>
                                        <td><xsl:value-of select="producer"/></td>
                                        <td><xsl:value-of select="model"/></td>
                                        <td><xsl:value-of select="@color"/></td>
                                        <td><xsl:value-of select="productionDate"/></td>
                                        <td><xsl:value-of select="price"/></td>
                                        <td><xsl:value-of select="quantity"/></td>
                                    </tr>
                                </xsl:for-each>
                            </xsl:for-each>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>