'use strict';

/* ============================================================
   TEKNOVATION
   Script principal unificado

   Compatible con:
   - index.html
   - shop.html
   - product.html
   - auth.html
   - cart.html
   - products.json
   ============================================================ */


/* ============================================================
   1. CONFIGURACIÓN
   ============================================================ */

const CONFIG = {

    PRODUCTS_URL: 'products.json',

    CART_KEY: 'cart',

    USERS_KEY: 'users',

    USER_KEY: 'currentUser',

    ORDERS_KEY: 'orders',

    FAVORITES_KEY: 'favorites',

    SLIDE_INTERVAL: 5000,

    FREE_SHIPPING: 39,

    SHIPPING_COST: 4.99,

    BUNDLE_MIN_ITEMS: 2,

    BUNDLE_DISCOUNT: 30,

    PROMO_CODES: {

        GAMING10: {

            type: 'percent',

            value: 10,

            label: '10% de descuento'

        },


        TEKNO20: {

            type: 'percent',

            value: 20,

            label: '20% de descuento'

        },


        BUNDLE30: {

            type: 'fixed',

            value: 30,

            label: '30€ de descuento'

        }

    }

};


/* ============================================================
   2. ESTADO GLOBAL
   ============================================================ */

const state = {

    productsData: null,

    cart: [],

    currentUser: null,

    favorites: [],

    appliedCoupon: null,

    currentProduct: null

};


/* ============================================================
   3. INICIALIZACIÓN PRINCIPAL
   ============================================================ */

document.addEventListener(

    'DOMContentLoaded',

    async () => {


        /*
         * Cargar carrito,
         * sesión y favoritos.
         */

        loadPersistentState();


        /*
         * Overlay para menú móvil.
         */

        createMobileOverlay();


        /*
         * Cargar productos antes
         * de inicializar búsqueda,
         * favoritos, shop, etc.
         */

        await loadProductsData();


        /*
         * Contadores del header.
         */

        updateCartCount();

        updateFavoritesCount();


        /*
         * Elementos generales.
         */

        initializeMobileMenu();

        initializeNewsletter();

        initializeScrollToTop();

        initializeSmoothScroll();

        initializeHeaderActions();


        /* ==================================================
           INDEX
           ================================================== */

        if (

            document.querySelector(
                '.hero'
            )

        ) {

            initializeHero();

        }


        /* ==================================================
           SHOP
           ================================================== */

        if (

            isPage(
                'shop.html'
            )

        ) {

            initializeShopPage();

        }


        /* ==================================================
           PRODUCT
           ================================================== */

        if (

            isPage(
                'product.html'
            )

        ) {

            initializeProductPage();

        }


        /* ==================================================
           AUTH
           ================================================== */

        if (

            isPage(
                'auth.html'
            )

        ) {

            initializeAuthPage();

        }


        /* ==================================================
           CART
           ================================================== */

        if (

            isPage(
                'cart.html'
            )

        ) {

            initializeCartPage();

        }

    }

);


/* ============================================================
   4. UTILIDADES GENERALES
   ============================================================ */

function isPage(
    pageName
) {

    return window
        .location
        .pathname
        .includes(
            pageName
        );

}


/* ============================================================
   ESCAPAR HTML
   ============================================================ */

function escapeHtml(
    value
) {

    if (

        value === null

        ||

        value === undefined

    ) {

        return '';

    }


    const div =
        document.createElement(
            'div'
        );


    div.textContent =
        String(
            value
        );


    return div.innerHTML;

}


/* ============================================================
   FORMATEAR PRECIO
   ============================================================ */

function formatPrice(
    value
) {

    const number =
        Number(
            value || 0
        );


    return new Intl.NumberFormat(

        'es-ES',

        {

            style: 'currency',

            currency: 'EUR'

        }

    ).format(
        number
    );

}


/* ============================================================
   GENERAR ID
   ============================================================ */

function generateId() {

    if (

        window.crypto

        &&

        typeof window.crypto.randomUUID
        === 'function'

    ) {

        return window.crypto
            .randomUUID();

    }


    return (

        Date.now()
            .toString(
                36
            )

        +

        Math.random()
            .toString(
                36
            )
            .slice(
                2
            )

    );

}


/* ============================================================
   JSON SEGURO
   ============================================================ */

function safeJSONParse(

    value,

    fallback

) {

    try {

        return value
            ? JSON.parse(
                value
            )
            : fallback;

    }

    catch (
        error
    ) {

        console.warn(

            '[Teknovation] JSON inválido:',

            error

        );


        return fallback;

    }

}


/* ============================================================
   5. LOCAL STORAGE
   ============================================================ */

function loadPersistentState() {


    /*
     * Carrito
     */

    state.cart =
        safeJSONParse(

            localStorage.getItem(
                CONFIG.CART_KEY
            ),

            []

        );


    /*
     * Usuario conectado
     */

    state.currentUser =
        safeJSONParse(

            localStorage.getItem(
                CONFIG.USER_KEY
            ),

            null

        );


    /*
     * Favoritos
     */

    state.favorites =
        safeJSONParse(

            localStorage.getItem(
                CONFIG.FAVORITES_KEY
            ),

            []

        );


    /*
     * Seguridad por si alguno
     * de los valores guardados
     * no tiene el formato correcto.
     */

    if (
        !Array.isArray(
            state.cart
        )
    ) {

        state.cart = [];

    }


    if (
        !Array.isArray(
            state.favorites
        )
    ) {

        state.favorites = [];

    }

}


/* ============================================================
   GUARDAR CARRITO
   ============================================================ */

function saveCart() {

    localStorage.setItem(

        CONFIG.CART_KEY,

        JSON.stringify(
            state.cart
        )

    );

}


/* ============================================================
   GUARDAR FAVORITOS
   ============================================================ */

function saveFavorites() {

    localStorage.setItem(

        CONFIG.FAVORITES_KEY,

        JSON.stringify(
            state.favorites
        )

    );

}


/* ============================================================
   OBTENER USUARIOS
   ============================================================ */

function getUsers() {

    const users =
        safeJSONParse(

            localStorage.getItem(
                CONFIG.USERS_KEY
            ),

            []

        );


    return Array.isArray(
        users
    )
        ? users
        : [];

}


/* ============================================================
   GUARDAR USUARIOS
   ============================================================ */

function saveUsers(
    users
) {

    localStorage.setItem(

        CONFIG.USERS_KEY,

        JSON.stringify(
            users
        )

    );

}


/* ============================================================
   OBTENER PEDIDOS
   ============================================================ */

function getOrders() {

    const orders =
        safeJSONParse(

            localStorage.getItem(
                CONFIG.ORDERS_KEY
            ),

            []

        );


    return Array.isArray(
        orders
    )
        ? orders
        : [];

}


/* ============================================================
   6. PRODUCTS.JSON
   ============================================================ */

async function loadProductsData() {


    /*
     * Si ya se cargaron,
     * no volver a hacer fetch.
     */

    if (
        state.productsData
    ) {

        return state.productsData;

    }


    try {

        const response =
            await fetch(
                CONFIG.PRODUCTS_URL
            );


        if (
            !response.ok
        ) {

            throw new Error(

                `HTTP ${response.status}`

            );

        }


        state.productsData =
            await response.json();


        /*
         * Verificación básica.
         */

        if (

            !state.productsData

            ||

            !Array.isArray(
                state.productsData.products
            )

        ) {

            state.productsData = {

                products: []

            };

        }


        return state.productsData;

    }

    catch (
        error
    ) {

        console.error(

            '[Teknovation] No se pudo cargar products.json:',

            error

        );


        state.productsData = {

            products: []

        };


        return state.productsData;

    }

}


/* ============================================================
   OBTENER PRODUCTO POR ID
   ============================================================ */

function getProductById(
    productId
) {

    if (

        !state.productsData

        ||

        !Array.isArray(
            state.productsData.products
        )

    ) {

        return null;

    }


    return (

        state.productsData
            .products
            .find(

                product =>

                    product.id
                    === productId

            )

        ||

        null

    );

}


/* ============================================================
   OBTENER IMAGEN
   ============================================================ */

function getProductImage(
    product
) {

    if (

        product

        &&

        Array.isArray(
            product.images
        )

        &&

        product.images.length > 0

    ) {

        return product.images[0];

    }


    return '';

}


/* ============================================================
   7. CARRITO - CONTADOR
   ============================================================ */

function getCartCount() {

    return state.cart.reduce(

        (

            total,

            item

        ) => {

            return (

                total

                +

                Number(
                    item.quantity || 0
                )

            );

        },

        0

    );

}


/* ============================================================
   ACTUALIZAR CONTADOR CARRITO
   ============================================================ */

function updateCartCount() {

    const count =
        getCartCount();


    document
        .querySelectorAll(
            '.cart-count'
        )
        .forEach(

            badge => {


                /*
                 * Número.
                 */

                badge.textContent =
                    count;


                badge.setAttribute(

                    'aria-label',

                    `${count} artículos en el carrito`

                );


                /*
                 * Si está vacío no mostramos
                 * el círculo rojo con 0.
                 */

                badge.style.display =
                    count > 0
                        ? 'block'
                        : 'none';

            }

        );

}


/* ============================================================
   8. HEADER
   ============================================================ */

function initializeHeaderActions() {


    /* ======================================================
       CUENTA
       ====================================================== */

    const accountBtn =
        document.getElementById(
            'accountBtn'
        );


    if (

        accountBtn

        &&

        accountBtn
            .tagName
            .toLowerCase()
        !== 'a'

    ) {

        accountBtn.addEventListener(

            'click',

            () => {

                window.location.href =
                    'auth.html';

            }

        );

    }


    /* ======================================================
       CARRITO
       ====================================================== */

    const cartBtn =
        document.getElementById(
            'cartBtn'
        );


    if (

        cartBtn

        &&

        cartBtn
            .tagName
            .toLowerCase()
        !== 'a'

    ) {

        cartBtn.addEventListener(

            'click',

            () => {

                window.location.href =
                    'cart.html';

            }

        );

    }


    /* ======================================================
       BUSCAR
       ====================================================== */

    const searchBtn =
        document.getElementById(
            'searchBtn'
        );


    searchBtn
        ?.addEventListener(

            'click',

            () => {

                openSearchModal();

            }

        );


    /* ======================================================
       FAVORITOS
       ====================================================== */

    const wishlistBtn =
        document.getElementById(
            'wishlistBtn'
        );


    wishlistBtn
        ?.addEventListener(

            'click',

            () => {

                openFavoritesModal();

            }

        );


    /*
     * Asegurar contadores
     * después de cargar el header.
     */

    updateCartCount();

    updateFavoritesCount();

}


/* ============================================================
   9. BUSCADOR + FAVORITOS
   ============================================================ */


/* ============================================================
   CREAR MODAL GENERAL
   ============================================================ */

function createStoreModal() {

    let overlay =
        document.getElementById(
            'storeModalOverlay'
        );


    /*
     * Si ya existe,
     * reutilizarlo.
     */

    if (
        overlay
    ) {

        return overlay;

    }


    overlay =
        document.createElement(
            'div'
        );


    overlay.id =
        'storeModalOverlay';


    overlay.className =
        'store-modal-overlay';


    overlay.innerHTML = `

        <div
            class="store-modal"
            role="dialog"
            aria-modal="true"
            aria-labelledby="storeModalTitle"
        >

            <div
                class="store-modal-header"
            >

                <h2
                    id="storeModalTitle"
                >
                    Teknovation
                </h2>


                <button
                    type="button"
                    class="store-modal-close"
                    id="storeModalClose"
                    aria-label="Cerrar"
                >

                    <span
                        class="material-icons"
                    >
                        close
                    </span>

                </button>

            </div>


            <div
                class="store-modal-body"
                id="storeModalBody"
            ></div>

        </div>

    `;


    document.body.appendChild(
        overlay
    );


    /*
     * Cerrar tocando el fondo.
     */

    overlay.addEventListener(

        'click',

        event => {

            if (

                event.target
                === overlay

            ) {

                closeStoreModal();

            }

        }

    );


    /*
     * Botón X.
     */

    overlay
        .querySelector(
            '#storeModalClose'
        )
        ?.addEventListener(

            'click',

            closeStoreModal

        );


    /*
     * ESC.
     */

    document.addEventListener(

        'keydown',

        event => {

            if (

                event.key === 'Escape'

                &&

                overlay
                    .classList
                    .contains(
                        'active'
                    )

            ) {

                closeStoreModal();

            }

        }

    );


    return overlay;

}


/* ============================================================
   ABRIR BUSCADOR
   ============================================================ */

function openSearchModal() {

    const overlay =
        createStoreModal();


    const title =
        document.getElementById(
            'storeModalTitle'
        );


    const body =
        document.getElementById(
            'storeModalBody'
        );


    if (
        !title
        ||
        !body
    ) {

        return;

    }


    title.textContent =
        'Buscar productos';


    body.innerHTML = `

        <div
            class="search-box-large"
        >

            <span
                class="material-icons"
            >
                search
            </span>


            <input
                type="search"
                id="globalSearchInput"
                placeholder="Buscar productos..."
                autocomplete="off"
                aria-label="Buscar productos"
            >

        </div>


        <div
            class="store-results"
            id="searchResults"
        ></div>

    `;


    overlay
        .classList
        .add(
            'active'
        );


    document.body.style.overflow =
        'hidden';


    const input =
        document.getElementById(
            'globalSearchInput'
        );


    /*
     * Mostrar productos inicialmente.
     */

    renderSearchResults(
        ''
    );


    input?.focus();


    input
        ?.addEventListener(

            'input',

            () => {

                renderSearchResults(
                    input.value
                );

            }

        );

}


/* ============================================================
   RESULTADOS DE BÚSQUEDA
   ============================================================ */

function renderSearchResults(
    searchText
) {

    const container =
        document.getElementById(
            'searchResults'
        );


    if (

        !container

        ||

        !state.productsData

        ||

        !Array.isArray(
            state.productsData.products
        )

    ) {

        return;

    }


    const search =
        String(
            searchText || ''
        )
            .trim()
            .toLowerCase();


    const products =
        state.productsData
            .products
            .filter(

                product => {


                    /*
                     * Sin texto:
                     * mostrar primeros productos.
                     */

                    if (
                        !search
                    ) {

                        return true;

                    }


                    const name =
                        String(
                            product.name || ''
                        )
                            .toLowerCase();


                    const subtitle =
                        String(
                            product.subtitle || ''
                        )
                            .toLowerCase();


                    const series =
                        String(
                            product.series || ''
                        )
                            .toLowerCase();


                    return (

                        name.includes(
                            search
                        )

                        ||

                        subtitle.includes(
                            search
                        )

                        ||

                        series.includes(
                            search
                        )

                    );

                }

            )
            .slice(
                0,
                8
            );


    /*
     * Sin resultados.
     */

    if (
        products.length === 0
    ) {

        container.innerHTML = `

            <div
                class="store-empty"
            >

                <span
                    class="material-icons"
                >
                    search_off
                </span>


                <h3>
                    No encontramos productos
                </h3>


                <p>
                    Intenta buscar con otro nombre.
                </p>

            </div>

        `;


        return;

    }


    /*
     * Productos.
     */

    container.innerHTML =
        products
            .map(

                product =>

                    createStoreResultHTML(
                        product
                    )

            )
            .join(
                ''
            );


    initializeStoreResultActions(
        container
    );

}


/* ============================================================
   ABRIR FAVORITOS
   ============================================================ */

function openFavoritesModal() {

    const overlay =
        createStoreModal();


    const title =
        document.getElementById(
            'storeModalTitle'
        );


    if (
        title
    ) {

        title.textContent =
            'Mis favoritos';

    }


    overlay
        .classList
        .add(
            'active'
        );


    document.body.style.overflow =
        'hidden';


    renderFavoritesModal();

}


/* ============================================================
   RENDER FAVORITOS
   ============================================================ */

function renderFavoritesModal() {

    const body =
        document.getElementById(
            'storeModalBody'
        );


    if (

        !body

        ||

        !state.productsData

        ||

        !Array.isArray(
            state.productsData.products
        )

    ) {

        return;

    }


    const products =
        state.productsData
            .products
            .filter(

                product =>

                    state.favorites
                        .includes(
                            product.id
                        )

            );


    /*
     * No hay favoritos.
     */

    if (
        products.length === 0
    ) {

        body.innerHTML = `

            <div
                class="store-empty"
            >

                <span
                    class="material-icons"
                >
                    favorite_border
                </span>


                <h3>
                    Todavía no tenés favoritos
                </h3>


                <p>
                    Tocá el corazón de un producto
                    para guardarlo aquí.
                </p>

            </div>

        `;


        return;

    }


    /*
     * Hay favoritos.
     */

    body.innerHTML = `

        <div
            class="store-results"
            id="favoritesResults"
        >

            ${
                products
                    .map(

                        product =>

                            createStoreResultHTML(
                                product
                            )

                    )
                    .join(
                        ''
                    )
            }

        </div>

    `;


    initializeStoreResultActions(
        body
    );

}


/* ============================================================
   HTML RESULTADO BUSCADOR / FAVORITOS
   ============================================================ */

function createStoreResultHTML(
    product
) {

    const favorite =
        state.favorites
            .includes(
                product.id
            );


    const image =
        getProductImage(
            product
        );


    return `

        <div
            class="store-result-item"
            data-store-product="${escapeHtml(
                product.id
            )}"
        >


            <a
                href="product.html?id=${encodeURIComponent(
                    product.id
                )}"
                class="store-result-image"
            >

                <img
                    src="${escapeHtml(
                        image
                    )}"
                    alt="${escapeHtml(
                        product.name
                    )}"
                >

            </a>


            <div
                class="store-result-info"
            >

                <a
                    href="product.html?id=${encodeURIComponent(
                        product.id
                    )}"
                >

                    <h3>
                        ${escapeHtml(
                            product.name
                        )}
                    </h3>

                </a>


                <p>
                    ${escapeHtml(
                        product.subtitle || ''
                    )}
                </p>


                <div
                    class="store-result-price"
                >
                    ${formatPrice(
                        product.price
                    )}
                </div>

            </div>


            <div
                class="store-result-actions"
            >


                ${
                    product.inStock
                        ? `

                            <button
                                type="button"
                                class="btn btn-primary store-add-cart"
                                data-product-id="${escapeHtml(
                                    product.id
                                )}"
                            >

                                <span
                                    class="material-icons"
                                >
                                    shopping_cart
                                </span>

                                Añadir

                            </button>

                          `
                        : `

                            <button
                                type="button"
                                class="btn btn-secondary"
                                disabled
                            >
                                Agotado
                            </button>

                          `
                }


                <button
                    type="button"
                    class="store-result-heart ${
                        favorite
                            ? 'active'
                            : ''
                    }"
                    data-favorite-product="${escapeHtml(
                        product.id
                    )}"
                    aria-label="${
                        favorite
                            ? 'Quitar de favoritos'
                            : 'Añadir a favoritos'
                    }"
                >

                    <span
                        class="material-icons"
                    >
                        ${
                            favorite
                                ? 'favorite'
                                : 'favorite_border'
                        }
                    </span>

                </button>

            </div>

        </div>

    `;

}


/* ============================================================
   ACCIONES EN BUSCADOR / FAVORITOS
   ============================================================ */

function initializeStoreResultActions(
    container
) {


    /*
     * Añadir al carrito.
     */

    container
        .querySelectorAll(
            '.store-add-cart'
        )
        .forEach(

            button => {

                button.addEventListener(

                    'click',

                    () => {

                        const productId =
                            button
                                .dataset
                                .productId;


                        addToCartById(
                            productId
                        );

                    }

                );

            }

        );


    /*
     * Favoritos.
     */

    container
        .querySelectorAll(
            '[data-favorite-product]'
        )
        .forEach(

            button => {

                button.addEventListener(

                    'click',

                    () => {

                        const productId =
                            button
                                .dataset
                                .favoriteProduct;


                        toggleFavorite(

                            productId,

                            button

                        );


                        /*
                         * Si el modal abierto
                         * es Favoritos,
                         * actualizar la lista.
                         */

                        const title =
                            document.getElementById(
                                'storeModalTitle'
                            );


                        if (

                            title

                            &&

                            title.textContent
                            === 'Mis favoritos'

                        ) {

                            renderFavoritesModal();

                        }

                    }

                );

            }

        );

}


/* ============================================================
   CERRAR MODAL
   ============================================================ */

function closeStoreModal() {

    const overlay =
        document.getElementById(
            'storeModalOverlay'
        );


    overlay
        ?.classList
        .remove(
            'active'
        );


    document.body.style.overflow =
        '';

}


/* ============================================================
   CONTADOR DE FAVORITOS
   ============================================================ */

function updateFavoritesCount() {

    const count =
        state.favorites.length;


    /*
     * Actualizamos cualquier contador
     * de favoritos presente en el header.
     */

    document
        .querySelectorAll(
            '.wishlist-count'
        )
        .forEach(

            badge => {

                badge.textContent =
                    count;


                badge.style.display =
                    count > 0
                        ? 'block'
                        : 'none';

            }

        );


    /*
     * Cambiar corazón del header.
     */

    document
        .querySelectorAll(
            '#wishlistBtn .material-icons'
        )
        .forEach(

            icon => {

                icon.textContent =
                    count > 0
                        ? 'favorite'
                        : 'favorite_border';

            }

        );

}


/* ============================================================
   TOGGLE FAVORITO
   ============================================================ */

function toggleFavorite(

    productId,

    button = null

) {

    const index =
        state.favorites
            .indexOf(
                productId
            );


    /*
     * Quitar favorito.
     */

    if (
        index >= 0
    ) {

        state.favorites.splice(

            index,

            1

        );

    }


    /*
     * Añadir favorito.
     */

    else {

        state.favorites.push(
            productId
        );

    }


    /*
     * Guardar.
     */

    saveFavorites();


    const isFavorite =
        state.favorites
            .includes(
                productId
            );


    /*
     * Actualizar todos los botones
     * visibles correspondientes al producto.
     */

    document
        .querySelectorAll(

            `
            .favorite-btn[data-product-id="${productId}"],
            [data-favorite-product="${productId}"]
            `

        )
        .forEach(

            favoriteButton => {

                favoriteButton
                    .classList
                    .toggle(

                        'active',

                        isFavorite

                    );


                favoriteButton.setAttribute(

                    'aria-label',

                    isFavorite
                        ? 'Quitar de favoritos'
                        : 'Añadir a favoritos'

                );


                const icon =
                    favoriteButton
                        .querySelector(
                            '.material-icons'
                        );


                if (
                    icon
                ) {

                    icon.textContent =
                        isFavorite
                            ? 'favorite'
                            : 'favorite_border';

                }

            }

        );


    /*
     * Si recibimos un botón específico
     * que no coincidió con el selector,
     * actualizarlo también.
     */

    if (
        button
    ) {

        button
            .classList
            .toggle(

                'active',

                isFavorite

            );


        const icon =
            button
                .querySelector(
                    '.material-icons'
                );


        if (
            icon
        ) {

            icon.textContent =
                isFavorite
                    ? 'favorite'
                    : 'favorite_border';

        }

    }


    /*
     * Contador del header
     * inmediatamente.
     */

    updateFavoritesCount();


    /*
     * Notificación.
     */

    notify(

        isFavorite
            ? 'Añadido a favoritos'
            : 'Eliminado de favoritos',

        'success'

    );

}


/* ============================================================
   10. HERO INDEX
   ============================================================ */

function initializeHero() {

    const slides =
        document.querySelectorAll(
            '.hero-slide'
        );


    const indicators =
        document.querySelectorAll(
            '.indicator'
        );


    if (
        slides.length === 0
    ) {

        return;

    }


    let currentSlide = 0;

    let interval = null;


    /* ======================================================
       MOSTRAR SLIDE
       ====================================================== */

    function showSlide(
        index
    ) {

        slides.forEach(

            slide =>

                slide
                    .classList
                    .remove(
                        'active'
                    )

        );


        indicators.forEach(

            indicator =>

                indicator
                    .classList
                    .remove(
                        'active'
                    )

        );


        currentSlide = (

            index

            +

            slides.length

        )

        % slides.length;


        slides[
            currentSlide
        ]
            .classList
            .add(
                'active'
            );


        if (

            indicators[
                currentSlide
            ]

        ) {

            indicators[
                currentSlide
            ]
                .classList
                .add(
                    'active'
                );

        }

    }


    /* ======================================================
       SIGUIENTE
       ====================================================== */

    function nextSlide() {

        showSlide(
            currentSlide + 1
        );

    }


    /* ======================================================
       AUTOPLAY
       ====================================================== */

    function startAutoplay() {

        clearInterval(
            interval
        );


        interval =
            setInterval(

                nextSlide,

                CONFIG.SLIDE_INTERVAL

            );

    }


    /*
     * Indicadores.
     */

    indicators.forEach(

        (

            indicator,

            index

        ) => {

            indicator.addEventListener(

                'click',

                () => {

                    showSlide(
                        index
                    );


                    startAutoplay();

                }

            );

        }

    );


    /*
     * Pausar al colocar mouse.
     */

    const hero =
        document.querySelector(
            '.hero'
        );


    if (
        hero
    ) {

        hero.addEventListener(

            'mouseenter',

            () => {

                clearInterval(
                    interval
                );

            }

        );


        hero.addEventListener(

            'mouseleave',

            startAutoplay

        );

    }


    showSlide(
        0
    );


    startAutoplay();

}


/* ============================================================
   11. SHOP
   ============================================================ */

async function initializeShopPage() {

    await loadProductsData();


    if (

        !state.productsData

        ||

        !Array.isArray(
            state.productsData.products
        )

    ) {

        return;

    }


    renderProducts(
        state.productsData.products
    );


    initializeFilters();

}


/* ============================================================
   RENDER PRODUCTOS SHOP
   ============================================================ */

function renderProducts(
    products
) {

    const grid =
        document.getElementById(
            'productsGrid'
        );


    if (
        !grid
    ) {

        return;

    }


    grid.innerHTML =

        products
            .map(

                product => {


                    const image =
                        getProductImage(
                            product
                        );


                    const isFavorite =
                        state.favorites
                            .includes(
                                product.id
                            );


                    /*
                     * Precio original.
                     */

                    const originalPrice =
                        product.originalPrice
                            ? `

                                <span
                                    class="price-original"
                                >
                                    ${formatPrice(
                                        product.originalPrice
                                    )}
                                </span>

                              `
                            : '';


                    /*
                     * Badges.
                     */

                    let badges =
                        '';


                    if (
                        product.isNew
                    ) {

                        badges += `

                            <div
                                class="product-badge"
                            >
                                Nuevo
                            </div>

                        `;

                    }


                    if (

                        Number(
                            product.discount
                        )

                        > 0

                    ) {

                        badges += `

                            <div
                                class="product-badge badge-offer"
                            >
                                -${product.discount}%
                            </div>

                        `;

                    }


                    /*
                     * Botón carrito.
                     */

                    let cartButton =
                        '';


                    if (
                        product.inStock
                    ) {

                        cartButton = `

                            <button
                                type="button"
                                class="btn btn-primary btn-add-cart"
                                data-product-id="${escapeHtml(
                                    product.id
                                )}"
                            >

                                <span
                                    class="material-icons"
                                >
                                    shopping_cart
                                </span>

                                Añadir al carrito

                            </button>

                        `;

                    }

                    else {

                        cartButton = `

                            <button
                                type="button"
                                class="btn btn-secondary"
                                disabled
                            >
                                Agotado
                            </button>

                        `;

                    }


                    return `

                        <div
                            class="product-card"
                            data-series="${escapeHtml(
                                product.series || ''
                            )}"
                            data-price="${Number(
                                product.price || 0
                            )}"
                            data-connectivity="${escapeHtml(
                                product.connectivity || ''
                            )}"
                            data-features="${escapeHtml(

                                (
                                    product.features || []
                                )
                                    .join(
                                        ','
                                    )

                            )}"
                        >


                            <a
                                href="product.html?id=${encodeURIComponent(
                                    product.id
                                )}"
                                class="product-link"
                            >


                                ${badges}


                                <div
                                    class="product-image"
                                >

                                    <img
                                        src="${escapeHtml(
                                            image
                                        )}"
                                        alt="${escapeHtml(
                                            product.name
                                        )}"
                                        class="product-img"
                                    >

                                </div>


                                <div
                                    class="product-info"
                                >

                                    <h3
                                        class="product-name"
                                    >
                                        ${escapeHtml(
                                            product.name
                                        )}
                                    </h3>


                                    <p
                                        class="product-subtitle"
                                    >
                                        ${escapeHtml(
                                            product.subtitle || ''
                                        )}
                                    </p>


                                    <div
                                        class="product-price"
                                    >

                                        <span
                                            class="price-current"
                                        >
                                            ${formatPrice(
                                                product.price
                                            )}
                                        </span>


                                        ${originalPrice}

                                    </div>


                                    ${
                                        !product.inStock
                                            ? `

                                                <div
                                                    class="product-status out-of-stock"
                                                >
                                                    Agotado
                                                </div>

                                              `
                                            : ''
                                    }

                                </div>

                            </a>


                            <div
                                class="product-actions"
                            >

                                ${cartButton}


                                <button
                                    type="button"
                                    class="btn-icon favorite-btn ${
                                        isFavorite
                                            ? 'active'
                                            : ''
                                    }"
                                    data-product-id="${escapeHtml(
                                        product.id
                                    )}"
                                    aria-label="${
                                        isFavorite
                                            ? 'Quitar de favoritos'
                                            : 'Añadir a favoritos'
                                    }"
                                >

                                    <span
                                        class="material-icons"
                                    >
                                        ${
                                            isFavorite
                                                ? 'favorite'
                                                : 'favorite_border'
                                        }
                                    </span>

                                </button>

                            </div>

                        </div>

                    `;

                }

            )
            .join(
                ''
            );


    /*
     * Actualizar contador
     * de productos visibles.
     */

    updateVisibleProductCount();


    /* ======================================================
       BOTONES AGREGAR AL CARRITO
       ====================================================== */

    grid
        .querySelectorAll(
            '.btn-add-cart'
        )
        .forEach(

            button => {

                button.addEventListener(

                    'click',

                    event => {

                        event.preventDefault();

                        event.stopPropagation();


                        const productId =
                            button
                                .dataset
                                .productId;


                        addToCartById(
                            productId
                        );

                    }

                );

            }

        );


    /* ======================================================
       BOTONES FAVORITOS
       ====================================================== */

    grid
        .querySelectorAll(
            '.favorite-btn'
        )
        .forEach(

            button => {

                button.addEventListener(

                    'click',

                    event => {

                        event.preventDefault();

                        event.stopPropagation();


                        toggleFavorite(

                            button
                                .dataset
                                .productId,

                            button

                        );

                    }

                );

            }

        );

}


/* ============================================================
   FILTROS DE SHOP
   ============================================================ */

function initializeFilters() {

    const sidebar =
        document.getElementById(
            'filtersSidebar'
        );


    if (
        !sidebar
    ) {

        return;

    }


    const filters =
        sidebar
            .querySelectorAll(

                '.filter-option input[type="checkbox"]'

            );


    const sortSelect =
        document.getElementById(
            'sortSelect'
        );


    const clearButton =
        document.querySelector(
            '.btn-clear-filters'
        );


    const mobileButton =
        document.getElementById(
            'mobileFiltersBtn'
        );


    const closeButton =
        document.getElementById(
            'closeFilters'
        );


    const overlay =
        document.getElementById(
            'mobileOverlay'
        );


    /*
     * Cada checkbox
     */

    filters.forEach(

        filter => {

            filter.addEventListener(

                'change',

                filterAndSortProducts

            );

        }

    );


    /*
     * Ordenar
     */

    if (
        sortSelect
    ) {

        sortSelect.addEventListener(

            'change',

            filterAndSortProducts

        );

    }


    /*
     * Limpiar filtros
     */

    clearButton
        ?.addEventListener(

            'click',

            () => {

                filters.forEach(

                    filter => {

                        filter.checked =
                            false;

                    }

                );


                if (
                    sortSelect
                ) {

                    sortSelect.value =
                        'featured';

                }


                filterAndSortProducts();

            }

        );


    /*
     * Abrir filtros móvil
     */

    mobileButton
        ?.addEventListener(

            'click',

            () => {

                sidebar
                    .classList
                    .add(
                        'active'
                    );


                overlay
                    ?.classList
                    .add(
                        'active'
                    );


                document.body.style.overflow =
                    'hidden';

            }

        );


    /*
     * Cerrar filtros.
     */

    closeButton
        ?.addEventListener(

            'click',

            closeFilters

        );


    overlay
        ?.addEventListener(

            'click',

            closeFilters

        );


    function closeFilters() {

        sidebar
            .classList
            .remove(
                'active'
            );


        overlay
            ?.classList
            .remove(
                'active'
            );


        document.body.style.overflow =
            '';

    }

}

function filterAndSortProducts() {

    if (
        !state.productsData
    ) {

        return;

    }


    const active = {

        connectivity: [],

        series: [],

        price: [],

        features: []

    };


    document
        .querySelectorAll(
            '.filter-option input[type="checkbox"]:checked'
        )
        .forEach(
            checkbox => {

                if (
                    active[
                        checkbox.name
                    ]
                ) {

                    active[
                        checkbox.name
                    ].push(
                        checkbox.value
                    );

                }

            }
        );


    let products =
        [
            ...state.productsData.products
        ];


    products =
        products.filter(
            product => {


                if (
                    active.connectivity.length
                    && !active.connectivity
                        .includes(
                            product.connectivity
                        )
                ) {

                    return false;

                }


                if (
                    active.series.length
                    && !active.series
                        .includes(
                            product.series
                        )
                ) {

                    return false;

                }


                if (
                    active.features.length
                ) {

                    const features =
                        product.features || [];


                    const matches =
                        active.features.every(
                            feature =>
                                features.includes(
                                    feature
                                )
                        );


                    if (!matches) {

                        return false;

                    }

                }


                if (
                    active.price.length
                ) {

                    const matches =
                        active.price.some(
                            range => {


                                if (
                                    range === '0-50'
                                ) {

                                    return (
                                        product.price
                                        < 50
                                    );

                                }


                                if (
                                    range === '50-100'
                                ) {

                                    return (
                                        product.price
                                        >= 50
                                        && product.price
                                        < 100
                                    );

                                }


                                if (
                                    range
                                    === '100-150'
                                ) {

                                    return (
                                        product.price
                                        >= 100
                                        && product.price
                                        < 150
                                    );

                                }


                                if (
                                    range === '150+'
                                ) {

                                    return (
                                        product.price
                                        >= 150
                                    );

                                }


                                return false;

                            }
                        );


                    if (!matches) {

                        return false;

                    }

                }


                return true;

            }
        );


    const sortSelect =
        document.getElementById(
            'sortSelect'
        );


    const sortValue =
        sortSelect
            ? sortSelect.value
            : 'featured';


    switch (sortValue) {

        case 'price-asc':

            products.sort(
                (
                    a,
                    b
                ) =>
                    a.price
                    - b.price
            );

            break;


        case 'price-desc':

            products.sort(
                (
                    a,
                    b
                ) =>
                    b.price
                    - a.price
            );

            break;


        case 'name':

            products.sort(
                (
                    a,
                    b
                ) =>
                    a.name.localeCompare(
                        b.name,
                        'es'
                    )
            );

            break;


        case 'newest':

            products.sort(
                (
                    a,
                    b
                ) =>
                    Number(
                        b.isNew
                    )
                    - Number(
                        a.isNew
                    )
            );

            break;


        case 'bestseller':

            products.sort(
                (
                    a,
                    b
                ) =>
                    Number(
                        b.reviewCount || 0
                    )
                    - Number(
                        a.reviewCount || 0
                    )
            );

            break;

    }


    renderProducts(
        products
    );

}


function updateVisibleProductCount() {

    const count =
        document.querySelectorAll(
            '.product-card'
        ).length;


    const countElement =
        document.getElementById(
            'productCount'
        );


    if (countElement) {

        countElement.textContent =
            count;

    }

}


/* ============================================================
   11. FAVORITOS
   ============================================================ */

function toggleFavorite(
    productId,
    button = null
) {

    const index =
        state.favorites
            .indexOf(
                productId
            );


    if (
        index >= 0
    ) {

        state.favorites.splice(
            index,
            1
        );

    } else {

        state.favorites.push(
            productId
        );

    }


    saveFavorites();


    const isFavorite =
        state.favorites
            .includes(
                productId
            );


    document
        .querySelectorAll(
            `[data-product-id="${productId}"].favorite-btn,
             [data-favorite-product="${productId}"]`
        )
        .forEach(
            favoriteButton => {

                favoriteButton
                    .classList
                    .toggle(
                        'active',
                        isFavorite
                    );


                const icon =
                    favoriteButton
                        .querySelector(
                            '.material-icons'
                        );


                if (icon) {

                    icon.textContent =
                        isFavorite
                            ? 'favorite'
                            : 'favorite_border';

                }

            }
        );


    if (button) {

        button.classList.toggle(
            'active',
            isFavorite
        );

    }


    updateFavoritesCount();


    notify(
        isFavorite
            ? 'Añadido a favoritos'
            : 'Eliminado de favoritos',
        'success'
    );

}


/* ============================================================
   12. PRODUCT PAGE
   ============================================================ */

async function initializeProductPage() {

    await loadProductsData();


    const params =
        new URLSearchParams(
            window.location.search
        );


    const productId =
        params.get('id')
        || 'pro-x2-superstrike';


    const product =
        getProductById(
            productId
        );


    if (!product) {

        showProductError();

        return;

    }


    state.currentProduct =
        product;


    renderProductPage(
        product
    );


    initializeProductQuantity();

    initializeProductTabs();

    initializeProductAddToCart();

}


function renderProductPage(
    product
) {

    document.title =
        `${product.name} | Teknovation`;


    const metaDescription =
        document.querySelector(
            'meta[name="description"]'
        );


    if (metaDescription) {

        metaDescription.setAttribute(
            'content',
            `${product.name} - ${product.subtitle}`
        );

    }


    const breadcrumb =
        document.getElementById(
            'breadcrumbProduct'
        )
        || document.querySelector(
            '.breadcrumb li[aria-current="page"]'
        );


    if (breadcrumb) {

        breadcrumb.textContent =
            product.name;

    }


    const series =
        document.getElementById(
            'productSeries'
        )
        || document.querySelector(
            '.product-badge-series'
        );


    if (series) {

        series.textContent =
            `${product.series.toUpperCase()} Series`;

    }


    const title =
        document.querySelector(
            '.product-title'
        );


    if (title) {

        title.textContent =
            product.name;

    }


    const description =
        document.querySelector(
            '.product-description'
        );


    if (description) {

        description.textContent =
            product.subtitle;

    }


    const price =
        document.querySelector(
            '.product-price-large .price-current'
        );


    if (price) {

        price.textContent =
            formatPrice(
                product.price
            );

    }


    const originalPrice =
        document.getElementById(
            'originalPrice'
        );


    if (originalPrice) {

        if (
            product.originalPrice
        ) {

            originalPrice.textContent =
                formatPrice(
                    product.originalPrice
                );


            originalPrice.style.display =
                'inline';

        } else {

            originalPrice.style.display =
                'none';

        }

    }


    const ratingCount =
        document.getElementById(
            'ratingCount'
        )
        || document.querySelector(
            '.rating-count'
        );


    if (ratingCount) {

        ratingCount.textContent =
            `(${product.reviewCount} reseñas)`;

    }


    renderProductStars(
        product.rating
    );


    renderProductImages(
        product
    );


    renderProductColors(
        product.colors || []
    );


    renderProductInformation(
        product
    );


    renderRelatedProducts(
        product
    );


    const details =
        document.querySelector(
            '.product-details'
        );


    if (details) {

        details.dataset.productId =
            product.id;

    }


    const addButton =
        document.getElementById(
            'addToCart'
        );


    if (
        addButton
        && !product.inStock
    ) {

        addButton.disabled =
            true;


        addButton.innerHTML = `
            <span class="material-icons">
                block
            </span>

            Agotado temporalmente
        `;

    }


    const loading =
        document.getElementById(
            'productLoading'
        );


    if (loading) {

        loading.style.display =
            'none';

    }


    const layout =
        document.getElementById(
            'productLayout'
        );


    if (layout) {

        layout.style.display =
            '';

    }


    const tabsSection =
        document.getElementById(
            'productTabsSection'
        );


    if (tabsSection) {

        tabsSection.style.display =
            '';

    }


    const relatedSection =
        document.getElementById(
            'relatedProductsSection'
        );


    if (relatedSection) {

        relatedSection.style.display =
            '';

    }

}


function renderProductImages(
    product
) {

    const main =
        document.getElementById(
            'galleryMain'
        )
        || document.querySelector(
            '.gallery-main'
        );


    const thumbs =
        document.getElementById(
            'galleryThumbs'
        )
        || document.querySelector(
            '.gallery-thumbs'
        );


    if (!main) {

        return;

    }


    const images =
        Array.isArray(
            product.images
        )
        && product.images.length
            ? product.images
            : [];


    main.innerHTML =
        images
            .map(
                (
                    image,
                    index
                ) => `
                    <div
                        class="gallery-image ${
                            index === 0
                                ? 'active'
                                : ''
                        }"
                    >

                        <img
                            src="${escapeHtml(image)}"
                            alt="${escapeHtml(product.name)}"
                            class="gallery-product-img"
                            style="
                                width: 100%;
                                height: 100%;
                                object-fit: contain;
                            "
                        >

                    </div>
                `
            )
            .join('');


    if (thumbs) {

        thumbs.innerHTML =
            images
                .map(
                    (
                        image,
                        index
                    ) => `
                        <button
                            type="button"
                            class="thumb ${
                                index === 0
                                    ? 'active'
                                    : ''
                            }"
                            data-index="${index}"
                        >

                            <img
                                src="${escapeHtml(image)}"
                                alt="Vista ${
                                    index + 1
                                } de ${escapeHtml(product.name)}"
                                style="
                                    width: 100%;
                                    height: 100%;
                                    object-fit: contain;
                                "
                            >

                        </button>
                    `
                )
                .join('');


        thumbs
            .querySelectorAll(
                '.thumb'
            )
            .forEach(
                button => {

                    button.addEventListener(
                        'click',
                        () => {

                            changeGalleryImage(
                                Number(
                                    button.dataset
                                        .index
                                )
                            );

                        }
                    );

                }
            );

    }

}


function changeGalleryImage(
    index
) {

    const images =
        document.querySelectorAll(
            '.gallery-image'
        );


    const thumbs =
        document.querySelectorAll(
            '.gallery-thumbs .thumb'
        );


    images.forEach(
        image =>
            image.classList.remove(
                'active'
            )
    );


    thumbs.forEach(
        thumb =>
            thumb.classList.remove(
                'active'
            )
    );


    if (
        images[index]
    ) {

        images[index]
            .classList.add(
                'active'
            );

    }


    if (
        thumbs[index]
    ) {

        thumbs[index]
            .classList.add(
                'active'
            );

    }

}


function renderProductColors(
    colors
) {

    const container =
        document.getElementById(
            'colorOptions'
        )
        || document.querySelector(
            '.color-options'
        );


    if (!container) {

        return;

    }


    container.innerHTML =
        colors
            .map(
                (
                    color,
                    index
                ) => {

                    const normalized =
                        normalizeColor(
                            color
                        );


                    const border =
                        color === 'Blanco'
                            ? 'border: 1px solid #ddd;'
                            : '';


                    return `
                        <button
                            type="button"
                            class="color-option ${
                                index === 0
                                    ? 'active'
                                    : ''
                            }"
                            data-color="${normalized}"
                            aria-label="${escapeHtml(color)}"
                        >

                            <span
                                class="color-swatch"
                                style="
                                    background:
                                        ${getColorHex(color)};
                                    ${border}
                                "
                            ></span>

                            <span class="color-name">
                                ${escapeHtml(color)}
                            </span>

                        </button>
                    `;

                }
            )
            .join('');


    container
        .querySelectorAll(
            '.color-option'
        )
        .forEach(
            button => {

                button.addEventListener(
                    'click',
                    () => {

                        container
                            .querySelectorAll(
                                '.color-option'
                            )
                            .forEach(
                                item =>
                                    item.classList.remove(
                                        'active'
                                    )
                            );


                        button.classList.add(
                            'active'
                        );

                    }
                );

            }
        );

}


function normalizeColor(
    color
) {

    const colors = {

        Negro: 'black',

        Blanco: 'white',

        Rosa: 'pink',

        Azul: 'blue',

        Lila: 'purple',

        Rojo: 'red',

        Verde: 'green'

    };


    return (
        colors[color]
        || String(color)
            .toLowerCase()
    );

}


function getColorHex(
    color
) {

    const colors = {

        Negro: '#000000',

        Blanco: '#ffffff',

        Rosa: '#ff69b4',

        Azul: '#1e90ff',

        Lila: '#9370db',

        Rojo: '#dc143c',

        Verde: '#32cd32'

    };


    return (
        colors[color]
        || '#777777'
    );

}


function renderProductStars(
    rating
) {

    const container =
        document.getElementById(
            'productStars'
        );


    if (!container) {

        return;

    }


    container.innerHTML = '';


    const full =
        Math.floor(
            Number(rating || 0)
        );


    const hasHalf =
        Number(rating || 0)
        - full
        >= 0.5;


    for (
        let index = 0;
        index < 5;
        index++
    ) {

        const star =
            document.createElement(
                'span'
            );


        star.className =
            'material-icons';


        if (
            index < full
        ) {

            star.textContent =
                'star';

        } else if (
            index === full
            && hasHalf
        ) {

            star.textContent =
                'star_half';

        } else {

            star.textContent =
                'star_border';

        }


        container.appendChild(
            star
        );

    }

}


function renderProductInformation(
    product
) {

    const fullDescription =
        document.getElementById(
            'fullDescription'
        );


    if (fullDescription) {

        fullDescription.textContent =
            product.description || '';

    }


    const featuresList =
        document.getElementById(
            'featuresList'
        );


    if (featuresList) {

        featuresList.innerHTML =
            (
                product.features_list
                || []
            )
                .map(
                    feature =>
                        `<li>${escapeHtml(feature)}</li>`
                )
                .join('');

    }


    const specsGrid =
        document.getElementById(
            'specsGrid'
        );


    if (
        specsGrid
        && product.specifications
    ) {

        specsGrid.innerHTML =
            Object
                .entries(
                    product.specifications
                )
                .filter(
                    (
                        [
                            key,
                            value
                        ]
                    ) =>
                        value !== null
                        && value !== ''
                )
                .map(
                    (
                        [
                            key,
                            value
                        ]
                    ) => `
                        <div class="spec-item">

                            <h4>
                                ${escapeHtml(
                                    formatSpecKey(
                                        key
                                    )
                                )}
                            </h4>

                            <p>
                                ${escapeHtml(value)}
                            </p>

                        </div>
                    `
                )
                .join('');

    }

}


function formatSpecKey(
    key
) {

    const names = {

        sensor:
            'Sensor',

        connectivity:
            'Conectividad',

        responseRate:
            'Velocidad de Respuesta',

        weight:
            'Peso',

        battery:
            'Batería',

        buttons:
            'Botones Programables',

        dimensions:
            'Dimensiones',

        compatibility:
            'Compatible con'

    };


    return names[key] || key;

}


function renderRelatedProducts(
    currentProduct
) {

    const container =
        document.getElementById(
            'relatedProductsGrid'
        );


    if (
        !container
        || !state.productsData
    ) {

        return;

    }


    const products =
        state.productsData.products
            .filter(
                product =>
                    product.id
                    !== currentProduct.id
            )
            .slice(
                0,
                4
            );


    container.innerHTML =
        products
            .map(
                product => `
                    <div class="product-card">

                        <a
                            href="product.html?id=${encodeURIComponent(product.id)}"
                            class="product-link"
                        >

                            <div class="product-image">

                                <img
                                    src="${escapeHtml(
                                        getProductImage(
                                            product
                                        )
                                    )}"
                                    alt="${escapeHtml(product.name)}"
                                    class="related-product-img"
                                    style="
                                        width: 100%;
                                        height: 190px;
                                        object-fit: contain;
                                    "
                                >

                            </div>

                            <div class="product-info">

                                <h3 class="product-name">
                                    ${escapeHtml(product.name)}
                                </h3>

                                <p class="product-subtitle">
                                    ${escapeHtml(product.subtitle)}
                                </p>

                                <div class="product-price">

                                    <span class="price-current">
                                        ${formatPrice(product.price)}
                                    </span>

                                </div>

                            </div>

                        </a>

                    </div>
                `
            )
            .join('');

}


function initializeProductQuantity() {

    const oldMinus =
        document.getElementById(
            'qtyMinus'
        );

    const oldPlus =
        document.getElementById(
            'qtyPlus'
        );

    const quantity =
        document.getElementById(
            'quantity'
        );


    if (
        !oldMinus
        || !oldPlus
        || !quantity
    ) {

        return;

    }


    /*
     * Crear botones limpios.
     *
     * Al clonar los botones se eliminan
     * listeners anteriores que pudieran
     * venir del HTML o de otra ejecución.
     */

    const minus =
        oldMinus.cloneNode(
            true
        );

    const plus =
        oldPlus.cloneNode(
            true
        );


    oldMinus.replaceWith(
        minus
    );

    oldPlus.replaceWith(
        plus
    );


    /*
     * Disminuir cantidad.
     */

    minus.addEventListener(
        'click',
        event => {

            event.preventDefault();
            event.stopPropagation();


            const currentValue =
                Number(
                    quantity.value
                );


            const minimum =
                Number(
                    quantity.min
                )
                || 1;


            if (
                currentValue > minimum
            ) {

                quantity.value =
                    String(
                        currentValue - 1
                    );

            }

        }
    );


    /*
     * Aumentar cantidad.
     */

    plus.addEventListener(
        'click',
        event => {

            event.preventDefault();
            event.stopPropagation();


            const currentValue =
                Number(
                    quantity.value
                );


            const maximum =
                Number(
                    quantity.max
                )
                || 10;


            if (
                currentValue < maximum
            ) {

                quantity.value =
                    String(
                        currentValue + 1
                    );

            }

        }
    );

}


function initializeProductTabs() {

    const buttons =
        document.querySelectorAll(
            '.tab-btn'
        );


    const panels =
        document.querySelectorAll(
            '.tab-panel'
        );


    buttons.forEach(
        button => {

            button.addEventListener(
                'click',
                () => {

                    buttons.forEach(
                        item =>
                            item.classList.remove(
                                'active'
                            )
                    );


                    panels.forEach(
                        panel =>
                            panel.classList.remove(
                                'active'
                            )
                    );


                    button.classList.add(
                        'active'
                    );


                    const panel =
                        document.getElementById(
                            button.dataset.tab
                        );


                    panel?.classList.add(
                        'active'
                    );

                }
            );

        }
    );

}


function initializeProductAddToCart() {

    const button =
        document.getElementById(
            'addToCart'
        );


    if (!button) {

        return;

    }


    button.addEventListener(
        'click',
        () => {

            if (
                !state.currentProduct
            ) {

                return;

            }


            if (
                !state.currentProduct
                    .inStock
            ) {

                notify(
                    'Este producto está agotado temporalmente.',
                    'warning'
                );

                return;

            }


            const quantity =
                Number(
                    document
                        .getElementById(
                            'quantity'
                        )
                        ?.value
                    || 1
                );


            const colorButton =
                document.querySelector(
                    '.color-option.active'
                );


            const color =
                colorButton
                    ?.dataset
                    .color
                || normalizeColor(
                    state.currentProduct
                        .colors?.[0]
                    || 'Negro'
                );


            addToCartById(
                state.currentProduct.id,
                quantity,
                color
            );

        }
    );

}


function showProductError() {

    const loading =
        document.getElementById(
            'productLoading'
        );


    const layout =
        document.getElementById(
            'productLayout'
        );


    const error =
        document.getElementById(
            'productError'
        );


    if (loading) {

        loading.style.display =
            'none';

    }


    if (layout) {

        layout.style.display =
            'none';

    }


    if (error) {

        error.style.display =
            'block';

    }

}


/* ============================================================
   13. OPERACIONES DEL CARRITO
   ============================================================ */

function addToCartById(
    productId,
    quantity = 1,
    color = null
) {

    const product =
        getProductById(
            productId
        );


    if (
        !product
        || !product.inStock
    ) {

        notify(
            'Este producto no está disponible.',
            'warning'
        );

        return;

    }


    const selectedColor =
        color
        || normalizeColor(
            product.colors?.[0]
            || 'Negro'
        );


    const existing =
        state.cart.find(
            item =>
                (
                    item.productId
                    === product.id
                    || item.id
                    === product.id
                )
                && item.color
                    === selectedColor
        );


    if (existing) {

        existing.quantity =
            Math.min(
                Number(
                    existing.quantity
                    || 0
                )
                + Number(quantity),
                10
            );

    } else {

        state.cart.push({

            cartId:
                generateId(),

            id:
                product.id,

            productId:
                product.id,

            name:
                product.name,

            subtitle:
                product.subtitle,

            price:
                Number(
                    product.price
                ),

            image:
                getProductImage(
                    product
                ),

            quantity:
                Math.min(
                    Number(quantity),
                    10
                ),

            color:
                selectedColor

        });

    }


    saveCart();

    updateCartCount();


    if (
        isPage(
            'cart.html'
        )
    ) {

        renderCartPage();

    }


    notify(
        `${product.name} añadido al carrito`,
        'success'
    );

}


/* ============================================================
   14. CART PAGE
   ============================================================ */

async function initializeCartPage() {

    await loadProductsData();


    renderCartPage();


    const clearButton =
        document.getElementById(
            'clearCartBtn'
        );


    clearButton?.addEventListener(
        'click',
        () => {

            state.cart = [];

            state.appliedCoupon =
                null;


            saveCart();

            renderCartPage();

        }
    );


    const applyCoupon =
        document.getElementById(
            'applyCoupon'
        );


    applyCoupon?.addEventListener(
        'click',
        applyCouponFromInput
    );


    const couponInput =
        document.getElementById(
            'couponInput'
        );


    couponInput?.addEventListener(
        'keydown',
        event => {

            if (
                event.key
                === 'Enter'
            ) {

                event.preventDefault();

                applyCouponFromInput();

            }

        }
    );


    const checkout =
        document.getElementById(
            'checkoutBtn'
        );


    checkout?.addEventListener(
        'click',
        handleCheckout
    );

}


function renderCartPage() {

    const empty =
        document.getElementById(
            'cartEmpty'
        );


    const content =
        document.getElementById(
            'cartContent'
        );


    const clearButton =
        document.getElementById(
            'clearCartBtn'
        );


    const tbody =
        document.getElementById(
            'cartTableBody'
        );


    if (
        state.cart.length === 0
    ) {

        if (empty) {

            empty.style.display =
                'block';

        }


        if (content) {

            content.style.display =
                'none';

        }


        if (clearButton) {

            clearButton.style.display =
                'none';

        }


        if (tbody) {

            tbody.innerHTML =
                '';

        }


        updateCartCount();

        updateCartSummary();

        updateCartPageTitle();

        return;

    }


    if (empty) {

        empty.style.display =
            'none';

    }


    if (content) {

        content.style.display =
            'grid';

    }


    if (clearButton) {

        clearButton.style.display =
            'inline-flex';

    }


    if (tbody) {

        tbody.innerHTML =
            state.cart
                .map(
                    (
                        item,
                        index
                    ) => {

                        const quantity =
                            Number(
                                item.quantity
                                || 1
                            );


                        const price =
                            Number(
                                item.price
                                || 0
                            );


                        const total =
                            quantity
                            * price;


                        return `
                            <tr>

                                <td>

                                    <div class="cart-product">

                                        <img
                                            src="${escapeHtml(
                                                item.image
                                                || ''
                                            )}"
                                            alt="${escapeHtml(
                                                item.name
                                            )}"
                                        >

                                        <div>

                                            <strong>
                                                ${escapeHtml(item.name)}
                                            </strong>

                                            <div class="cart-product-color">
                                                Color:
                                                ${escapeHtml(
                                                    displayColor(
                                                        item.color
                                                    )
                                                )}
                                            </div>

                                        </div>

                                    </div>

                                </td>


                                <td>
                                    ${formatPrice(price)}
                                </td>


                                <td>

                                    <div class="cart-quantity">

                                        <button
                                            type="button"
                                            data-cart-action="minus"
                                            data-index="${index}"
                                        >
                                            -
                                        </button>

                                        <span>
                                            ${quantity}
                                        </span>

                                        <button
                                            type="button"
                                            data-cart-action="plus"
                                            data-index="${index}"
                                        >
                                            +
                                        </button>

                                    </div>

                                </td>


                                <td>
                                    ${formatPrice(total)}
                                </td>


                                <td>

                                    <button
                                        type="button"
                                        class="remove-cart-item"
                                        data-cart-action="remove"
                                        data-index="${index}"
                                        aria-label="Eliminar ${escapeHtml(item.name)}"
                                    >

                                        <span class="material-icons">
                                            delete
                                        </span>

                                    </button>

                                </td>

                            </tr>
                        `;

                    }
                )
                .join('');


        tbody
            .querySelectorAll(
                '[data-cart-action]'
            )
            .forEach(
                button => {

                    button.addEventListener(
                        'click',
                        () => {

                            handleCartAction(
                                button.dataset
                                    .cartAction,
                                Number(
                                    button.dataset
                                        .index
                                )
                            );

                        }
                    );

                }
            );

    }


    updateCartCount();

    updateCartSummary();

    updateCartPageTitle();

}


function handleCartAction(
    action,
    index
) {

    const item =
        state.cart[index];


    if (!item) {

        return;

    }


    if (
        action === 'plus'
    ) {

        item.quantity =
            Math.min(
                Number(
                    item.quantity
                    || 1
                )
                + 1,
                10
            );

    }


    if (
        action === 'minus'
    ) {

        item.quantity =
            Math.max(
                Number(
                    item.quantity
                    || 1
                )
                - 1,
                1
            );

    }


    if (
        action === 'remove'
    ) {

        state.cart.splice(
            index,
            1
        );

    }


    saveCart();

    renderCartPage();

}


function updateCartPageTitle() {

    const element =
        document.getElementById(
            'cartPageTitle'
        );


    if (!element) {

        return;

    }


    const count =
        getCartCount();


    element.textContent =
        count > 0
            ? `(${count})`
            : '';

}


function getCartSubtotal() {

    return state.cart.reduce(
        (
            total,
            item
        ) => {

            return (
                total
                + Number(
                    item.price || 0
                )
                * Number(
                    item.quantity || 0
                )
            );

        },
        0
    );

}


function getCouponDiscount(
    subtotal
) {

    if (
        !state.appliedCoupon
    ) {

        return 0;

    }


    if (
        state.appliedCoupon.type
        === 'percent'
    ) {

        return (
            subtotal
            * state.appliedCoupon.value
            / 100
        );

    }


    return Number(
        state.appliedCoupon.value
        || 0
    );

}


function updateCartSummary() {

    const itemCount =
        getCartCount();


    const subtotal =
        getCartSubtotal();


    const automaticDiscount =
        itemCount
        >= CONFIG.BUNDLE_MIN_ITEMS
            ? CONFIG.BUNDLE_DISCOUNT
            : 0;


    const couponDiscount =
        getCouponDiscount(
            subtotal
        );


    const totalDiscount =
        Math.min(
            subtotal,
            automaticDiscount
            + couponDiscount
        );


    const afterDiscount =
        Math.max(
            subtotal
            - totalDiscount,
            0
        );


    const shipping =
        afterDiscount === 0
        || afterDiscount
            >= CONFIG.FREE_SHIPPING
            ? 0
            : CONFIG.SHIPPING_COST;


    const total =
        afterDiscount
        + shipping;


    setText(
        'summaryItemCount',
        itemCount
    );


    setText(
        'summarySubtotal',
        formatPrice(
            subtotal
        )
    );


    setText(
        'summaryShipping',
        shipping === 0
            ? 'Gratis'
            : formatPrice(
                shipping
            )
    );


    setText(
        'summaryTotal',
        formatPrice(
            total
        )
    );


    const discountRow =
        document.getElementById(
            'discountRow'
        );


    if (discountRow) {

        discountRow.style.display =
            totalDiscount > 0
                ? 'flex'
                : 'none';

    }


    setText(
        'summaryDiscount',
        `-${formatPrice(totalDiscount)}`
    );


    const promo =
        document.getElementById(
            'promoAlert'
        );


    const promoText =
        document.getElementById(
            'promoAlertText'
        );


    if (
        promo
        && promoText
    ) {

        if (
            itemCount === 1
        ) {

            promo.style.display =
                'flex';


            promoText.textContent =
                'Añade 1 artículo más y obtén 30€ de descuento';

        } else if (
            itemCount >= 2
        ) {

            promo.style.display =
                'flex';


            promoText.textContent =
                '¡Descuento de 30€ aplicado!';

        } else {

            promo.style.display =
                'none';

        }

    }

}


function applyCouponFromInput() {

    const input =
        document.getElementById(
            'couponInput'
        );


    const feedback =
        document.getElementById(
            'couponFeedback'
        );


    if (
        !input
        || !feedback
    ) {

        return;

    }


    const code =
        input.value
            .trim()
            .toUpperCase();


    const promo =
        CONFIG.PROMO_CODES[
            code
        ];


    if (!promo) {

        state.appliedCoupon =
            null;


        feedback.textContent =
            'Código de descuento no válido.';


        feedback.className =
            'coupon-feedback error';


        updateCartSummary();

        return;

    }


    state.appliedCoupon = {
        code,
        ...promo
    };


    feedback.textContent =
        `Cupón aplicado: ${promo.label}.`;


    feedback.className =
        'coupon-feedback success';


    updateCartSummary();

}


async function handleCheckout() {

    if (
        state.cart.length === 0
    ) {

        notify(
            'Añade productos antes de finalizar la compra.',
            'warning'
        );

        return;

    }


    if (
        !state.currentUser
    ) {

        if (
            typeof Swal
            !== 'undefined'
        ) {

            const result =
                await Swal.fire({

                    icon:
                        'info',

                    title:
                        'Inicia sesión',

                    text:
                        'Debes iniciar sesión antes de finalizar la compra.',

                    showCancelButton:
                        true,

                    confirmButtonText:
                        'Ir a Mi Cuenta',

                    cancelButtonText:
                        'Cancelar',

                    confirmButtonColor:
                        '#00b8db',

                    cancelButtonColor:
                        '#777777'

                });


            if (
                result.isConfirmed
            ) {

                window.location.href =
                    'auth.html';

            }

        } else {

            window.location.href =
                'auth.html';

        }


        return;

    }


    const orderNumber =
        `TKN-${Math.floor(
            10000
            + Math.random()
            * 90000
        )}`;


    const orders =
        getOrders();


    orders.push({

        number:
            orderNumber,

        date:
            new Date()
                .toISOString(),

        user:
            state.currentUser,

        items:
            state.cart.map(
                item => ({
                    ...item
                })
            ),

        subtotal:
            getCartSubtotal()

    });


    localStorage.setItem(
        CONFIG.ORDERS_KEY,
        JSON.stringify(
            orders
        )
    );


    state.cart = [];

    state.appliedCoupon = null;


    saveCart();

    updateCartCount();


    if (
        typeof Swal
        !== 'undefined'
    ) {

        await Swal.fire({

            icon:
                'success',

            title:
                '¡Compra finalizada!',

            html: `
                <div class="checkout-success-content">

                    <p>
                        Tu pedido fue realizado correctamente.
                    </p>

                    <div class="checkout-order-number">
                        Número de pedido

                        <strong>
                            #${orderNumber}
                        </strong>
                    </div>

                    <p class="checkout-success-message">
                        ¡Gracias por comprar en Teknovation!
                    </p>

                </div>
            `,

            confirmButtonText:
                'Continuar',

            confirmButtonColor:
                '#00b8db',

            allowOutsideClick:
                false,

            allowEscapeKey:
                false

        });


        renderCartPage();


        window.location.href =
            'index.html';

    } else {

        alert(
            `Compra finalizada correctamente. Pedido: ${orderNumber}`
        );


        window.location.href =
            'index.html';

    }

}


function displayColor(
    color
) {

    const names = {

        black: 'Negro',

        white: 'Blanco',

        pink: 'Rosa',

        blue: 'Azul',

        purple: 'Lila',

        red: 'Rojo',

        green: 'Verde'

    };


    return (
        names[color]
        || color
        || 'No especificado'
    );

}


/* ============================================================
   15. AUTH PAGE
   ============================================================ */

   async function initializeAuthPage() {

    /*
     * Configurar pestañas.
     */

    initializeAuthTabs();


    /*
     * Mostrar / ocultar contraseña.
     */

    initializePasswordToggles();


    /*
     * Formato del teléfono.
     */

    initializePhoneInput();


    /*
     * Fortaleza de contraseña.
     */

    initializePasswordStrength();


    /*
     * Registro.
     */

    initializeRegisterForm();


    /*
     * Login.
     */

    initializeLoginForm();


    /*
     * Cerrar sesión.
     */

    initializeLogout();


    /*
     * Recuperación de contraseña.
     */

    initializeForgotPassword();


    /*
     * Refrescar contador del carrito.
     */

    updateCartCount();


    /*
     * Refrescar favoritos.
     */

    updateFavoritesCount();


    /*
     * Mostrar sesión si ya existe.
     */

    showCurrentSession();

}


/* ============================================================
   TABS LOGIN / REGISTRO
   ============================================================ */

function initializeAuthTabs() {

    const loginTab =
        document.getElementById(
            'loginTab'
        );


    const registerTab =
        document.getElementById(
            'registerTab'
        );


    const loginPanel =
        document.getElementById(
            'loginPanel'
        );


    const registerPanel =
        document.getElementById(
            'registerPanel'
        );


    if (
        !loginTab
        || !registerTab
        || !loginPanel
        || !registerPanel
    ) {

        return;

    }


    loginTab.addEventListener(
        'click',
        () => {

            loginTab.classList.add(
                'active'
            );


            registerTab.classList.remove(
                'active'
            );


            loginPanel.classList.add(
                'active'
            );


            registerPanel.classList.remove(
                'active'
            );


            loginTab.setAttribute(
                'aria-selected',
                'true'
            );


            registerTab.setAttribute(
                'aria-selected',
                'false'
            );

        }
    );


    registerTab.addEventListener(
        'click',
        () => {

            registerTab.classList.add(
                'active'
            );


            loginTab.classList.remove(
                'active'
            );


            registerPanel.classList.add(
                'active'
            );


            loginPanel.classList.remove(
                'active'
            );


            registerTab.setAttribute(
                'aria-selected',
                'true'
            );


            loginTab.setAttribute(
                'aria-selected',
                'false'
            );

        }
    );

}


/* ============================================================
   MOSTRAR / OCULTAR CONTRASEÑAS
   ============================================================ */

function initializePasswordToggles() {

    document
        .querySelectorAll(
            '.toggle-password'
        )
        .forEach(
            button => {

                button.addEventListener(
                    'click',
                    () => {

                        const targetId =
                            button.dataset.target;


                        const input =
                            targetId
                                ? document.getElementById(
                                    targetId
                                )
                                : button
                                    .closest(
                                        '.input-wrapper'
                                    )
                                    ?.querySelector(
                                        'input[type="password"], input[type="text"][data-password-input]'
                                    );


                        if (!input) {

                            return;

                        }


                        const icon =
                            button.querySelector(
                                '.material-icons'
                            );


                        const isHidden =
                            input.type
                            === 'password';


                        input.type =
                            isHidden
                                ? 'text'
                                : 'password';


                        if (icon) {

                            icon.textContent =
                                isHidden
                                    ? 'visibility'
                                    : 'visibility_off';

                        }


                        button.setAttribute(
                            'aria-label',
                            isHidden
                                ? 'Ocultar contraseña'
                                : 'Mostrar contraseña'
                        );

                    }
                );

            }
        );

}


/* ============================================================
   TELÉFONO
   ============================================================ */

function initializePhoneInput() {

    const phone =
        document.getElementById(
            'phone'
        );


    if (!phone) {

        return;

    }


    phone.addEventListener(
        'input',
        () => {

            let value =
                phone.value
                    .replace(
                        /\D/g,
                        ''
                    )
                    .slice(
                        0,
                        8
                    );


            if (
                value.length > 4
            ) {

                value =
                    value.slice(
                        0,
                        4
                    )
                    + '-'
                    + value.slice(
                        4
                    );

            }


            phone.value =
                value;

        }
    );

}


/* ============================================================
   FORTALEZA DE CONTRASEÑA
   ============================================================ */

function initializePasswordStrength() {

    const password =
        document.getElementById(
            'registerPassword'
        );


    if (!password) {

        return;

    }


    password.addEventListener(
        'input',
        () => {

            const value =
                password.value;


            let level = 0;


            if (
                value.length >= 8
            ) {

                level++;

            }


            if (
                /[A-Z]/.test(
                    value
                )
            ) {

                level++;

            }


            if (
                /[0-9]/.test(
                    value
                )
            ) {

                level++;

            }


            if (
                /[^A-Za-z0-9]/.test(
                    value
                )
            ) {

                level++;

            }


            for (
                let index = 1;
                index <= 4;
                index++
            ) {

                const bar =
                    document.getElementById(
                        `strengthBar${index}`
                    );


                if (!bar) {

                    continue;

                }


                bar.style.opacity =
                    index <= level
                        ? '1'
                        : '0.25';


                bar.classList.remove(
                    'weak',
                    'medium',
                    'good',
                    'strong'
                );


                if (
                    index <= level
                ) {

                    if (
                        level === 1
                    ) {

                        bar.classList.add(
                            'weak'
                        );

                    } else if (
                        level === 2
                    ) {

                        bar.classList.add(
                            'medium'
                        );

                    } else if (
                        level === 3
                    ) {

                        bar.classList.add(
                            'good'
                        );

                    } else if (
                        level === 4
                    ) {

                        bar.classList.add(
                            'strong'
                        );

                    }

                }

            }


            const labels = [

                '',

                'Débil',

                'Aceptable',

                'Buena',

                'Fuerte'

            ];


            const label =
                document.getElementById(
                    'strengthLabel'
                );


            if (label) {

                label.textContent =
                    labels[level];

            }

        }
    );

}


/* ============================================================
   REGISTRO
   ============================================================ */

function initializeRegisterForm() {

    const form =
        document.getElementById(
            'registerForm'
        );


    if (!form) {

        return;

    }


    form.addEventListener(
        'submit',
        event => {

            event.preventDefault();


            clearRegisterErrors();


            const firstName =
                document.getElementById(
                    'firstName'
                )
                    ?.value
                    .trim()
                || '';


            const lastName =
                document.getElementById(
                    'lastName'
                )
                    ?.value
                    .trim()
                || '';


            const email =
                document.getElementById(
                    'registerEmail'
                )
                    ?.value
                    .trim()
                    .toLowerCase()
                || '';


            const phone =
                document.getElementById(
                    'phone'
                )
                    ?.value
                    .trim()
                || '';


            const password =
                document.getElementById(
                    'registerPassword'
                )
                    ?.value
                || '';


            const confirmPassword =
                document.getElementById(
                    'confirmPassword'
                )
                    ?.value
                || '';


            const acceptTerms =
                document.getElementById(
                    'acceptTerms'
                )
                    ?.checked
                || false;


            const newsletter =
                document.getElementById(
                    'newsletterOptin'
                )
                    ?.checked
                || false;


            let valid =
                true;


            /* ==================================================
               NOMBRE
               ================================================== */

            if (
                firstName.length < 2
            ) {

                showFieldError(
                    'firstNameError',
                    'Ingresa un nombre válido.'
                );


                valid =
                    false;

            }


            /* ==================================================
               APELLIDO
               ================================================== */

            if (
                lastName.length < 2
            ) {

                showFieldError(
                    'lastNameError',
                    'Ingresa un apellido válido.'
                );


                valid =
                    false;

            }


            /* ==================================================
               EMAIL
               ================================================== */

            if (
                !validateEmail(
                    email
                )
            ) {

                showFieldError(
                    'registerEmailError',
                    'Ingresa un correo electrónico válido.'
                );


                valid =
                    false;

            }


            /* ==================================================
               CONTRASEÑA
               ================================================== */

            if (
                password.length < 8
            ) {

                showFieldError(
                    'registerPasswordError',
                    'La contraseña debe tener al menos 8 caracteres.'
                );


                valid =
                    false;

            }


            /* ==================================================
               CONFIRMAR CONTRASEÑA
               ================================================== */

            if (
                password
                !== confirmPassword
            ) {

                showFieldError(
                    'confirmPasswordError',
                    'Las contraseñas no coinciden.'
                );


                valid =
                    false;

            }


            /* ==================================================
               TÉRMINOS
               ================================================== */

            if (
                !acceptTerms
            ) {

                showFieldError(
                    'termsError',
                    'Debes aceptar los términos.'
                );


                valid =
                    false;

            }


            if (!valid) {

                return;

            }


            /* ==================================================
               REVISAR USUARIO EXISTENTE
               ================================================== */

            const users =
                getUsers();


            const exists =
                users.some(
                    user =>
                        String(
                            user.email || ''
                        )
                            .toLowerCase()
                        === email
                );


            if (
                exists
            ) {

                showAuthMessage(
                    'registerGlobalMessage',
                    'Ya existe una cuenta con este correo.',
                    'error'
                );


                return;

            }


            /* ==================================================
               CREAR USUARIO
               ================================================== */

            const user = {

                id:
                    generateId(),

                firstName,

                lastName,

                name:
                    `${firstName} ${lastName}`
                        .trim(),

                email,

                phone,

                password,

                newsletter,

                createdAt:
                    new Date()
                        .toISOString()

            };


            users.push(
                user
            );


            saveUsers(
                users
            );


            /*
             * Iniciar sesión automáticamente
             * al registrarse.
             */

            state.currentUser =
                user;


            localStorage.setItem(
                CONFIG.USER_KEY,
                JSON.stringify(
                    user
                )
            );


            form.reset();


            showAuthSuccess(
                '¡Cuenta creada!',
                `Bienvenido, ${firstName}. Tu cuenta se creó correctamente.`
            );


            notify(
                'Cuenta creada correctamente.',
                'success'
            );

        }
    );

}


/* ============================================================
   LOGIN
   ============================================================ */

function initializeLoginForm() {

    const form =
        document.getElementById(
            'loginForm'
        );


    if (!form) {

        return;

    }


    form.addEventListener(
        'submit',
        event => {

            event.preventDefault();


            clearLoginErrors();


            const email =
                document.getElementById(
                    'loginEmail'
                )
                    ?.value
                    .trim()
                    .toLowerCase()
                || '';


            const password =
                document.getElementById(
                    'loginPassword'
                )
                    ?.value
                || '';


            let valid =
                true;


            /* ==================================================
               EMAIL
               ================================================== */

            if (
                !validateEmail(
                    email
                )
            ) {

                showFieldError(
                    'loginEmailError',
                    'Ingresa un correo electrónico válido.'
                );


                valid =
                    false;

            }


            /* ==================================================
               PASSWORD
               ================================================== */

            if (
                password.length < 1
            ) {

                showFieldError(
                    'loginPasswordError',
                    'Ingresa tu contraseña.'
                );


                valid =
                    false;

            }


            if (!valid) {

                return;

            }


            const users =
                getUsers();


            const user =
                users.find(
                    registeredUser =>

                        String(
                            registeredUser.email
                            || ''
                        )
                            .toLowerCase()
                        === email

                        &&

                        String(
                            registeredUser.password
                            || ''
                        )
                        === password
                );


            if (!user) {

                showAuthMessage(
                    'loginGlobalMessage',
                    'Correo o contraseña incorrectos.',
                    'error'
                );


                return;

            }


            /*
             * Guardar sesión.
             */

            state.currentUser =
                user;


            localStorage.setItem(
                CONFIG.USER_KEY,
                JSON.stringify(
                    user
                )
            );


            showAuthSuccess(
                '¡Bienvenido!',
                `Has iniciado sesión como ${user.firstName || user.name || user.email}.`
            );


            notify(
                'Sesión iniciada correctamente.',
                'success'
            );

        }
    );

}


/* ============================================================
   MOSTRAR SESIÓN ACTUAL
   ============================================================ */

function showCurrentSession() {

    if (
        !state.currentUser
    ) {

        return;

    }


    showAuthSuccess(
        '¡Bienvenido!',
        `Has iniciado sesión como ${
            state.currentUser.firstName
            || state.currentUser.name
            || state.currentUser.email
        }.`
    );

}


/* ============================================================
   SUCCESS AUTH
   ============================================================ */

function showAuthSuccess(
    title,
    message
) {

    const loginPanel =
        document.getElementById(
            'loginPanel'
        );


    const registerPanel =
        document.getElementById(
            'registerPanel'
        );


    const tabs =
        document.querySelector(
            '.auth-tabs'
        );


    const success =
        document.getElementById(
            'authSuccess'
        );


    const successTitle =
        document.getElementById(
            'successTitle'
        );


    const successMessage =
        document.getElementById(
            'successMessage'
        );


    if (!success) {

        return;

    }


    if (loginPanel) {

        loginPanel.style.display =
            'none';

    }


    if (registerPanel) {

        registerPanel.style.display =
            'none';

    }


    if (tabs) {

        tabs.style.display =
            'none';

    }


    if (successTitle) {

        successTitle.textContent =
            title;

    }


    if (successMessage) {

        successMessage.textContent =
            message;

    }


    success.style.display =
        'block';

}


/* ============================================================
   CERRAR SESIÓN
   ============================================================ */

function initializeLogout() {

    const logout =
        document.getElementById(
            'logoutBtn'
        );


    logout?.addEventListener(
        'click',
        () => {

            state.currentUser =
                null;


            localStorage.removeItem(
                CONFIG.USER_KEY
            );


            if (
                typeof Swal
                !== 'undefined'
            ) {

                Swal.fire({

                    icon:
                        'success',

                    title:
                        'Sesión cerrada',

                    text:
                        'Has cerrado sesión correctamente.',

                    timer:
                        1200,

                    showConfirmButton:
                        false

                }).then(
                    () => {

                        window.location.reload();

                    }
                );

            } else {

                window.location.reload();

            }

        }
    );

}


/* ============================================================
   OLVIDÉ MI CONTRASEÑA
   ============================================================ */

function initializeForgotPassword() {

    const button =
        document.getElementById(
            'forgotPasswordBtn'
        );


    button?.addEventListener(
        'click',
        async () => {

            if (
                typeof Swal
                === 'undefined'
            ) {

                notify(
                    'Consulta con soporte para recuperar tu contraseña.',
                    'info'
                );

                return;

            }


            const result =
                await Swal.fire({

                    icon:
                        'question',

                    title:
                        'Recuperar contraseña',

                    input:
                        'email',

                    inputLabel:
                        'Correo electrónico',

                    inputPlaceholder:
                        'tu@email.com',

                    confirmButtonText:
                        'Buscar cuenta',

                    cancelButtonText:
                        'Cancelar',

                    showCancelButton:
                        true,

                    confirmButtonColor:
                        '#00b8db'

                });


            if (
                !result.isConfirmed
                || !result.value
            ) {

                return;

            }


            const email =
                String(
                    result.value
                )
                    .trim()
                    .toLowerCase();


            const users =
                getUsers();


            const user =
                users.find(
                    item =>
                        String(
                            item.email || ''
                        )
                            .toLowerCase()
                        === email
                );


            if (!user) {

                await Swal.fire({

                    icon:
                        'error',

                    title:
                        'Cuenta no encontrada',

                    text:
                        'No existe una cuenta registrada con ese correo.'

                });


                return;

            }


            /*
             * Para este proyecto local mostramos
             * una confirmación simulada.
             */

            await Swal.fire({

                icon:
                    'success',

                title:
                    'Cuenta encontrada',

                text:
                    'En una aplicación real recibirías un enlace de recuperación en tu correo.',

                confirmButtonColor:
                    '#00b8db'

            });

        }
    );

}


/* ============================================================
   VALIDACIÓN EMAIL
   ============================================================ */

function validateEmail(
    email
) {

    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        .test(
            String(
                email
            )
        );

}


/* ============================================================
   ERROR DE CAMPO
   ============================================================ */

function showFieldError(
    id,
    message
) {

    const element =
        document.getElementById(
            id
        );


    if (!element) {

        return;

    }


    element.textContent =
        message;


    element.classList.add(
        'error'
    );

}


/* ============================================================
   MENSAJE GLOBAL AUTH
   ============================================================ */

function showAuthMessage(
    id,
    message,
    type = ''
) {

    const element =
        document.getElementById(
            id
        );


    if (!element) {

        return;

    }


    element.textContent =
        message;


    element.className =
        `auth-global-message ${type}`;

}


/* ============================================================
   LIMPIAR ERRORES LOGIN
   ============================================================ */

function clearLoginErrors() {

    [

        'loginEmailError',

        'loginPasswordError'

    ]
        .forEach(
            id => {

                const element =
                    document.getElementById(
                        id
                    );


                if (element) {

                    element.textContent =
                        '';

                }

            }
        );


    showAuthMessage(
        'loginGlobalMessage',
        '',
        ''
    );

}


/* ============================================================
   LIMPIAR ERRORES REGISTRO
   ============================================================ */

function clearRegisterErrors() {

    [

        'firstNameError',

        'lastNameError',

        'registerEmailError',

        'phoneError',

        'registerPasswordError',

        'confirmPasswordError',

        'termsError'

    ]
        .forEach(
            id => {

                const element =
                    document.getElementById(
                        id
                    );


                if (element) {

                    element.textContent =
                        '';

                }

            }
        );


    showAuthMessage(
        'registerGlobalMessage',
        '',
        ''
    );

}


/* ============================================================
   16. MENÚ MÓVIL
   ============================================================ */

function createMobileOverlay() {

    if (
        document.getElementById(
            'mobileOverlay'
        )
    ) {

        return;

    }


    const overlay =
        document.createElement(
            'div'
        );


    overlay.id =
        'mobileOverlay';


    overlay.className =
        'mobile-overlay';


    document.body.appendChild(
        overlay
    );

}


function initializeMobileMenu() {

    const button =
        document.querySelector(
            '.mobile-menu-toggle'
        );


    const nav =
        document.querySelector(
            '.nav-menu'
        );


    const overlay =
        document.getElementById(
            'mobileOverlay'
        );


    if (
        !button
        || !nav
    ) {

        return;

    }


    button.addEventListener(
        'click',
        () => {

            const active =
                nav.classList.toggle(
                    'active'
                );


            overlay
                ?.classList
                .toggle(
                    'active',
                    active
                );


            document.body.style.overflow =
                active
                    ? 'hidden'
                    : '';

        }
    );


    overlay?.addEventListener(
        'click',
        () => {

            nav.classList.remove(
                'active'
            );


            overlay.classList.remove(
                'active'
            );


            document.body.style.overflow =
                '';

        }
    );

}


/* ============================================================
   17. NEWSLETTER
   ============================================================ */

function initializeNewsletter() {

    document
        .querySelectorAll(
            '.newsletter-form'
        )
        .forEach(
            form => {

                form.addEventListener(
                    'submit',
                    event => {

                        event.preventDefault();


                        const input =
                            form.querySelector(
                                'input[type="email"]'
                            );


                        const email =
                            input
                                ?.value
                                .trim()
                            || '';


                        if (
                            !validateEmail(
                                email
                            )
                        ) {

                            notify(
                                'Ingresa un correo electrónico válido.',
                                'warning'
                            );


                            return;

                        }


                        notify(
                            '¡Gracias por suscribirte!',
                            'success'
                        );


                        form.reset();

                    }
                );

            }
        );

}


/* ============================================================
   18. SCROLL
   ============================================================ */

function initializeScrollToTop() {

    const button =
        document.getElementById(
            'scrollToTop'
        );


    if (!button) {

        return;

    }


    window.addEventListener(
        'scroll',
        () => {

            button.style.display =
                window.scrollY > 400
                    ? 'flex'
                    : 'none';

        }
    );


    button.addEventListener(
        'click',
        () => {

            window.scrollTo({

                top:
                    0,

                behavior:
                    'smooth'

            });

        }
    );

}


/* ============================================================
   SCROLL SUAVE
   ============================================================ */

function initializeSmoothScroll() {

    document
        .querySelectorAll(
            'a[href^="#"]'
        )
        .forEach(
            link => {

                link.addEventListener(
                    'click',
                    event => {

                        const href =
                            link.getAttribute(
                                'href'
                            );


                        if (
                            !href
                            || href === '#'
                        ) {

                            return;

                        }


                        const target =
                            document.querySelector(
                                href
                            );


                        if (!target) {

                            return;

                        }


                        event.preventDefault();


                        target.scrollIntoView({

                            behavior:
                                'smooth',

                            block:
                                'start'

                        });

                    }
                );

            }
        );

}


/* ============================================================
   19. STORAGE EVENT
   Para actualizar carrito y favoritos entre pestañas.
   ============================================================ */

window.addEventListener(
    'storage',
    event => {

        /* ==================================================
           CARRITO
           ================================================== */

        if (
            event.key
            === CONFIG.CART_KEY
        ) {

            state.cart =
                safeJSONParse(
                    localStorage.getItem(
                        CONFIG.CART_KEY
                    ),
                    []
                );


            if (
                !Array.isArray(
                    state.cart
                )
            ) {

                state.cart =
                    [];

            }


            updateCartCount();


            if (
                isPage(
                    'cart.html'
                )
            ) {

                renderCartPage();

            }

        }


        /* ==================================================
           FAVORITOS
           ================================================== */

        if (
            event.key
            === CONFIG.FAVORITES_KEY
        ) {

            state.favorites =
                safeJSONParse(
                    localStorage.getItem(
                        CONFIG.FAVORITES_KEY
                    ),
                    []
                );


            if (
                !Array.isArray(
                    state.favorites
                )
            ) {

                state.favorites =
                    [];

            }


            updateFavoritesCount();


            const title =
                document.getElementById(
                    'storeModalTitle'
                );


            if (
                title?.textContent
                === 'Mis favoritos'
            ) {

                renderFavoritesModal();

            }

        }


        /* ==================================================
           USUARIO
           ================================================== */

        if (
            event.key
            === CONFIG.USER_KEY
        ) {

            state.currentUser =
                safeJSONParse(
                    localStorage.getItem(
                        CONFIG.USER_KEY
                    ),
                    null
                );

        }

    }
);


/* ============================================================
   20. UTILIDADES DOM
   ============================================================ */

function setText(
    id,
    value
) {

    const element =
        document.getElementById(
            id
        );


    if (
        element
    ) {

        element.textContent =
            value;

    }

}


/* ============================================================
   21. NOTIFICACIONES
   ============================================================ */

function notify(
    message,
    type = 'info'
) {

    /*
     * SweetAlert2 si está disponible.
     */

    if (
        typeof Swal
        !== 'undefined'
    ) {

        const icons = {

            success:
                'success',

            error:
                'error',

            warning:
                'warning',

            info:
                'info'

        };


        Swal.fire({

            icon:
                icons[type]
                || 'info',

            title:
                type === 'success'
                    ? 'Listo'
                    : 'Teknovation',

            text:
                message,

            timer:
                type === 'success'
                    ? 1500
                    : undefined,

            showConfirmButton:
                type !== 'success',

            confirmButtonColor:
                '#00b8db',

            toast:
                type === 'success',

            position:
                type === 'success'
                    ? 'top-end'
                    : 'center'

        });


        return;

    }


    /*
     * Fallback sin SweetAlert.
     */

    console.log(
        `[${type}] ${message}`
    );

}

