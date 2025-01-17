
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Login V3</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">



        <meta name="robots" content="noindex, follow">
    <body>
        <div class="container" id="container">
            <div class="form-container sign-up-container">
                <form action="registerUser" method="post">
                    <h1>Crear Cuenta</h1>

                    <div class="slider">


                        <input type="hidden" class="form-control"  id="txtIDCargo" name="txtIDCargo" value="1">
                        <input type="text" id="txtNombre" name="txtNombre" placeholder="Nombre" required/>
                        <input type="text" id="txtApellido" name="txtApellido" placeholder="Apellido" required />
                        <input type="text" id="txtDireccion" name="txtDireccion" placeholder="Direccion" required />
                        <input type="email" id="txtCorreo" name="txtCorreo" placeholder="Correo" required/>
                        <input type="text" id="txtFechaNac" name="txtFechaNac" placeholder="Fecha de Nacimiento" required />
                        <input type="text" id="txtIdentificacion" name="txtIdentificacion" placeholder="Idenditificacion" required />
                        <input type="password" id="txtContrasena" name="txtContrasena" placeholder="Contrasena" required />
                        <input type="text" id="txtGenero" name="txtGenero" placeholder="Genero" required />
                        <input type="text" id="txtTelf" name="txtTelf" placeholder="Numero de Telefono" required />
                        <input type="hidden" class="form-control"  id="txtEstado" name="txtEstado" value="true">
                    </div>
                    <button
                        type="submit" name="accion"  value="Guardar" >Guardar
                    </button>
                </form>
            </div>
            <div class="form-container sign-in-container">
                <form action="loginClients" method="post">
                    <h1>Login</h1>
                    <div class="social-container">

                    </div>

                    <input type="email" id="txtCorreo" name="txtCorreo" placeholder="correo@example.com" required/>
                    <input type="password" id="txtContrasena" name="txtContrasena" placeholder="Password" required/>

                    <button vtype="submit" name="accion" value="iniciar">Ingresar</button>
                </form>
            </div>
            <div class="overlay-container">
                <div class="overlay">
                    <div class="overlay-panel overlay-left">
                        <h1>Bienvenido de nuevo!</h1>
                        <p>Inicia sesion con tus datos correctos.</p>
                        <button class="ghost" id="signIn">Login</button>
                    </div>
                    <div class="overlay-panel overlay-right">
                        <h1>Hola, Bienvenido</h1>
                        <p>No tienes una cuenta?</p>
                        <button class="ghost" id="signUp">Registrarse</button>
                    </div>
                </div>
            </div>
        </div>

        <style>
            @import url('https://fonts.googleapis.com/css?family=Montserrat:400,800');

            * {
                box-sizing: border-box;
            }
            .slider {
                width: 100%;
                top: 0;
                height: 600px;
                overflow: auto;
            }

            .slider input {
                display: block;
                top: 40px;
                width: 100%;
                margin-bottom: 10px;
            }
            .slider button {
                bottom: 30px;
            }

            body {
                background-image: url('https://cdn.wallpapersafari.com/33/3/uJwBHK.jpg');
                background-size: cover;
                background-position: center;
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
                font-family: 'Montserrat', sans-serif;
                height: 100vh;
                margin: -20px 0 50px;
            }

            h1 {
                font-weight: bold;
                margin: 0;
            }

            h2 {
                text-align: center;
            }

            p {
                font-size: 14px;
                font-weight: 100;
                line-height: 20px;
                letter-spacing: 0.5px;
                margin: 20px 0 30px;
            }

            span {
                font-size: 12px;
            }

            a {
                color: #333;
                font-size: 14px;
                text-decoration: none;
                margin: 15px 0;
            }

            button {
                border-radius: 20px;
                border: 1px solid #87CEEB;
                background-color: #87CEEB;
                color: #FFFFFF;
                font-size: 12px;
                font-weight: bold;
                padding: 12px 45px;
                letter-spacing: 1px;
                text-transform: uppercase;
                transition: transform 80ms ease-in;
            }

            button:active {
                transform: scale(0.95);
            }

            button:focus {
                outline: none;
            }

            button.ghost {
                background-color: transparent;
                border-color: #FFFFFF;
            }

            form {
                background-color: #FFFFFF;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                padding: 0 50px;
                height: 100%;
                text-align: center;
            }

            input {
                background-color: #eee;
                border: none;
                padding: 12px 15px;
                margin: 8px 0;
                width: 100%;
            }

            .container {
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25),
                    0 10px 10px rgba(0, 0, 0, 0.22);
                position: relative;
                overflow: hidden;
                width: 768px;
                max-width: 100%;
                min-height: 480px;
            }

            .form-container {
                position: absolute;
                top: 0;
                height: 100%;
                transition: all 0.6s ease-in-out;
            }

            .sign-in-container {
                left: 0;
                width: 50%;
                z-index: 2;
            }

            .container.right-panel-active .sign-in-container {
                transform: translateX(100%);
            }

            .sign-up-container {
                left: 0;
                width: 50%;
                opacity: 0;
                z-index: 1;
            }

            .container.right-panel-active .sign-up-container {
                transform: translateX(100%);
                opacity: 1;
                z-index: 5;
                animation: show 0.6s;
            }

            @keyframes show {

                0%,
                49.99% {
                    opacity: 0;
                    z-index: 1;
                }

                50%,
                100% {
                    opacity: 1;
                    z-index: 5;
                }
            }

            .overlay-container {
                position: absolute;
                top: 0;
                left: 50%;
                width: 50%;
                height: 100%;
                overflow: hidden;
                transition: transform 0.6s ease-in-out;
                z-index: 100;
            }

            .container.right-panel-active .overlay-container {
                transform: translateX(-100%);
            }

            .overlay {
                background:  #64B5F6;
                background: -webkit-linear-gradient(to right, #87CEEB, #64B5F6);
                background: linear-gradient(to right, #87CEEB, #64B5F6);
                background-repeat: no-repeat;
                background-size: cover;
                background-position: 0 0;
                color: #FFFFFF;
                position: relative;
                left: -100%;
                height: 100%;
                width: 200%;
                transform: translateX(0);
                transition: transform 0.6s ease-in-out;
            }

            .container.right-panel-active .overlay {
                transform: translateX(50%);
            }

            .overlay-panel {
                position: absolute;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                padding: 0 40px;
                text-align: center;
                top: 0;
                height: 100%;
                width: 50%;
                transform: translateX(0);
                transition: transform 0.6s ease-in-out;
            }

            .overlay-left {
                transform: translateX(-20%);
            }

            .container.right-panel-active .overlay-left {
                transform: translateX(0);
            }

            .overlay-right {
                right: 0;
                transform: translateX(0);
            }

            .container.right-panel-active .overlay-right {
                transform: translateX(20%);
            }

            .social-container {
                margin: 20px 0;
            }

            .social-container a {
                border: 1px solid #DDDDDD;
                border-radius: 50%;
                display: inline-flex;
                justify-content: center;
                align-items: center;
                margin: 0 5px;
                height: 40px;
                width: 40px;
            }

            footer {
                background-color: #222;
                color: #fff;
                font-size: 14px;
                bottom: 0;
                position: fixed;
                left: 0;
                right: 0;
                text-align: center;
                z-index: 999;
            }

            footer p {
                margin: 10px 0;
            }

            footer i {
                color: red;
            }

            footer a {
                color: #87CEEB;
                text-decoration: none;
            }
        </style>
        <script>
            const signUpButton = document.getElementById('signUp');
            const signInButton = document.getElementById('signIn');
            const container = document.getElementById('container');
            signUpButton.addEventListener('click', () => {
                container.classList.add("right-panel-active");
            });
            signInButton.addEventListener('click', () => {
                container.classList.remove("right-panel-active");
            });
        </script>

    </body>
</html>
