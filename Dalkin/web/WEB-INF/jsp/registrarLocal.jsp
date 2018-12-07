<%@page import="Model.Cliente"%>
<%@page import="Model.Comuna"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Registrar Local</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="Demo project">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="styles/bootstrap4/bootstrap.min.css">
        <link href="plugins/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="plugins/OwlCarousel2-2.2.1/owl.carousel.css">
        <link rel="stylesheet" type="text/css" href="plugins/OwlCarousel2-2.2.1/owl.theme.default.css">
        <link rel="stylesheet" type="text/css" href="plugins/OwlCarousel2-2.2.1/animate.css">
        <link rel="stylesheet" type="text/css" href="plugins/jquery.mb.YTPlayer-3.1.12/jquery.mb.YTPlayer.css">
        <link rel="stylesheet" type="text/css" href="styles/contact.css">
        <link rel="stylesheet" type="text/css" href="styles/contact_responsive.css">
    </head>
    <body>

        <div class="super_container">

            <!-- Header -->

            <header class="header">
                <div class="container">
                    <div class="row">
                        <div class="col">
                            <div class="header_content d-flex flex-row align-items-center justify-content-start">
                                <div class="logo"><a href="index.htm">Dalkin</a></div>
                                <nav class="main_nav">
                                    <ul>
                                        <li class="active"><a href="index.htm">Inicio</a></li>
                                        <li><a href="registrarLocal.htm">Registrar Local</a></li>
                                        <li><a href="registrarUsuario.htm">Registrar Usuario</a></li>
                                        <li><a href="ingresarUsuario.htm">Ingresar</a></li>
                                    </ul>
                                </nav>
                                <div class="search_container ml-auto">
                                    <div class="weather">
                                        <div class="temperature">+10°</div>
                                        <img class="weather_icon" src="images/cloud.png" alt="">
                                    </div>
                                    <form action="#">
                                        <input type="search" class="header_search_input" required="required" placeholder="Type to Search...">
                                        <img class="header_search_icon" src="images/search.png" alt="">
                                    </form>

                                </div>
                                <div class="hamburger ml-auto menu_mm">
                                    <i class="fa fa-bars trans_200 menu_mm" aria-hidden="true"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </header>

            <!-- Menu -->

            <div class="menu d-flex flex-column align-items-end justify-content-start text-right menu_mm trans_400">
                <div class="menu_close_container"><div class="menu_close"><div></div><div></div></div></div>
                <div class="logo menu_mm"><a href="index.htm">Dalkin</a></div>
                <div class="search">
                    <form action="#">
                        <input type="search" class="header_search_input menu_mm" required="required" placeholder="Type to Search...">
                        <img class="header_search_icon menu_mm" src="images/search_2.png" alt="">
                    </form>
                </div>
                <nav class="menu_nav">
                    <ul class="menu_mm">
                       <li class="active"><a href="index.htm">Inicio</a></li>
                                        <li><a href="registrarLocal.htm">Registrar Local</a></li>
                                        <li><a href="registrarUsuario.htm">Registrar Usuario</a></li>
                                        <li><a href="ingresarUsuario.htm">Ingresar</a></li>
                    </ul>
                </nav>
            </div>

            <!-- Home -->

            <div class="home">
                <div class="home_background parallax-window" data-parallax="scroll" data-image-src="images/regular.jpg" data-speed="0.8"></div>
                <div class="home_content">
                    <div class="container">
                        <div class="column">
                            <div class="col-lg-7 offset-lg-2 ">

                                <!-- Post Comment -->
                                <div class="post_comment">
                                    <div class="contact_form_container">
                                        
                                        
                                        <form action="guardar_cliente.htm" method="POST">

                                            <input type="text"  placeholder="Nombre" required="required"  name="nombre">  <br><br/>
                                            <input type="text"  placeholder="Descripcion" required="required" name="descripcion">  <br><br/>
                                            <input type="text"  placeholder="Rut" required="required" name="rut">  <br><br/>
                                            <input type="text"  placeholder="Direccion" required="required" name="direccion">  <br><br/>
                                            <input type="text"  placeholder="Telefono" required="required"  name="telefono">  <br><br/>
                                            <input type="text"  placeholder="Correo" required="required"  name="correo">  <br><br/>


                                            <!--    //mostra combo de comuna  -->
                                            <%
                                                List<Comuna> comunas = (List<Comuna>) request.getAttribute("comunas");
                                            %>

                                            <select id="comuna" name="comuna">
                                                <%
                                                    for (Comuna comuna : comunas) {
                                                %>
                                                <option value="<% out.write(comuna.getId().toString());%>"><% out.write(comuna.getNombre());%></option>
                                                <%
                                                    }
                                                %>
                                            </select> 



                                            <!-- //mostra combo de cliente -->
                                            <%
                                                List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
                                            %>

                                            <select id="cliente" name="cliente">
                                                <%
                                                    for (Cliente cliente : clientes) {
                                                %>
                                                <option value="<% out.write(cliente.getId().toString());%>"><% out.write(cliente.getNombre());%></option>
                                                <%
                                                    }
                                                %>
                                            </select> 

                                            <br><br/>



                                            <button type="submit" >Guardar</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>		
                </div>
            </div>

            <!-- Footer -->

            <footer class="footer">
                <div class="container">
                    <div class="row row-lg-eq-height">
                        <div class="col-lg-9 order-lg-1 order-2">
                            <div class="footer_content">
                                <div class="footer_logo"><a href="#">Dalkin</a></div>
                                <div class="footer_social">

                                </div>
                                <div class="copyright"><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                    Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></div>
                            </div>
                        </div>

                    </div>
                </div>
            </footer>
        </div>

        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="styles/bootstrap4/popper.js"></script>
        <script src="styles/bootstrap4/bootstrap.min.js"></script>
        <script src="plugins/OwlCarousel2-2.2.1/owl.carousel.js"></script>
        <script src="plugins/easing/easing.js"></script>
        <script src="plugins/masonry/masonry.js"></script>
        <script src="plugins/parallax-js-master/parallax.min.js"></script>
        <script src="js/contact.js"></script>
    </body>
</html>