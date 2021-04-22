<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:alura="http://alura.com.br">

    <xsl:template match="/movimentacoes">

        <!-- aqui vem o template -->
        <html xmlns:alura="http://alura.com.br">
            <body>
            <table>
                <tr>
                    <th>Valor</th>
                    <th>Data</th>
                    <th>Tipo</th>
                </tr>
                <xsl:for-each select="movimentacao">
                    <tr>
                        <th><xsl:value-of select="valor" /></th>
                        <th><xsl:value-of select="data" /></th>
                        <th><xsl:value-of select="tipo" /></th>
                    </tr>
                </xsl:for-each>
            </table>
            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>