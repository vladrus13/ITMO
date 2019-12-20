<template>
    <!--suppress HtmlUnknownTag -->
    <body id="app">
    <Header :user="user"/>
    <Middle :posts="posts" :users="users" :user="user" :comments="comments"/>
    <Footer/>
    </body>
</template>

<script>
    import Header from './components/Header'
    import Middle from './components/Middle'
    import Footer from './components/Footer'
    import axios from 'axios'

    export default {
        name: 'app',
        data: function () {
            return {
                user: null,
                posts: [],
                users: [],
                comments: []
            }
        },
        components: {
            Header,
            Middle,
            Footer
        }, beforeCreate() {
            axios.get("/api/1/posts").then(posts => this.posts = posts["data"]);
            axios.get("/api/1/users").then(users => this.users = users["data"]);
            axios.get("/api/1/comment").then(comments => this.comments = comments["data"]);

            this.$root.$on("onLogout", () => {
                localStorage.removeItem("jwt");
                this.user = null;
            });

            this.$root.$on("onJwt", (jwt, enter) => {
                axios.defaults.headers = {
                    Authorization: "Bearer " + jwt
                };

                axios.get("/api/1/users/authorized").then(response => {
                    this.user = response.data;
                    if (enter) {
                        this.$root.$emit("onEnterSuccess");
                    }
                });
            });

            this.$root.$on("onEnter", (login, password) => {
                axios.post("/api/1/jwt", {
                    login: login,
                    password: password
                }).then(response => {
                    localStorage.setItem("jwt", response.data);
                    this.$root.$emit("onJwt", response.data, true);
                }).catch(error => {
                    this.$root.$emit("onEnterValidationError", error.response.data);
                });
            });

            this.$root.$on("onAddPost", (title, text) => {
                if (this.user == null) {
                    this.$root.$emit("onAddPostValidationError", "WHO ARE YOU???");
                } else {
                    if (title.length < 1 || title.length > 15) {
                        this.$root.$emit("onAddPostValidationError", "Your title have a bad length. Must [1; 15]. Your - " + title.length);
                    } else {
                        if (text.length < 1) {
                            this.$root.$emit("onAddPostValidationError", "Your text must be not empty");
                        } else {
                            axios.post("/api/1/posts", {
                                title: title,
                                text: text
                            }).then(response => {
                                axios.get("/api/1/posts").then(posts => this.posts = posts["data"]);
                                response.data;
                                this.$root.$emit("onChangePage", 'Index');
                            }).catch(error => {
                                this.$root.$emit("onEnterValidationError", error.response.data);
                            });
                        }
                    }
                }
            });

            this.$root.$on("onRegister", (login, password, name) => {
                if (!/^[a-z]+$/.test(login) || login.length < 3 || login.length > 16) {
                    // login is invalid
                    if (!/^[a-z]+$/.test(login)) {
                        this.$root.$emit("onRegisterValidationError", "Login is uncorrected. Only small Latin!");
                    } else {
                        this.$root.$emit("onRegisterValidationError", "Login is uncorrected. Length must be on [3, 16]");
                    }
                } else {
                    if (password.length < 4 || password.length > 32) {
                        this.$root.$emit("onRegisterValidationError", "Password is uncorrected. Length must be on [4, 32]. Your - " + password.length);
                    } else {
                        if (name.length < 1 || name.length > 32) {
                            this.$root.$emit("onRegisterValidationError", "Name is uncorrected. Length must be on [1, 32]. Your - " + name.length);
                        } else {
                            axios.post("/api/1/users", {
                                login: login,
                                password: password,
                                name: name
                            }).then(response => {
                                localStorage.setItem("jwt", response.data);
                                axios.get("/api/1/users").then(users => this.users = users["data"]);
                                this.$root.$emit("onJwt", response.data, true);
                            }).catch(error => {
                                this.$root.$emit("onRegisterValidationError", error.response.data);
                            });
                        }
                    }
                }
            });

            this.$root.$on("onAddComment", (postId, text) => {
                if (this.user == null) {
                    this.$root.$emit("onCommentPostValidationError", "WHO ARE YOU???");
                } else {
                    if (text.length < 1) {
                        this.$root.$emit("onAddCommentValidationError", "Your text must be not empty");
                    } else {
                        axios.post("/api/1/comment", {
                            postId: postId.toString(),
                            text: text
                        }).then(response => {
                            axios.get("/api/1/comment").then(comments => this.comments = comments["data"]);
                            response.data;
                            this.$root.$emit("onChangePage", 'Index');
                        }).catch(error => {
                            this.$root.$emit("onCommentValidationError", error.response.data);
                        });
                    }

                }
            });

        }, beforeMount() {
            if (localStorage.getItem("jwt") && !this.user) {
                this.$root.$emit("onJwt", localStorage.getItem("jwt"), true);
            }
        }
    }
</script>

<style>
</style>
