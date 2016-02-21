$(document).ready(function(){
    window.gdvf = window.gdvf || {};

    function createElement(tag, attributes, style, appendTarget) {
        var elem = document.createElement(tag);

        for (var attr in attributes) {
            elem[attr] = attributes[attr];
        }

        if (style) {
            for (var property in style) {
                elem.style[property] = style[property];
            }
        }

        if (appendTarget) {
            appendTarget.appendChild(elem);
        }

        return elem;
    }

    gdvf.hisCom = {
        msgs: [],

        init: function init() {
            var wrapper = createElement('div', {
                class: "his_com"
            }, {
                display: "none",
                width: "25%",
                height: "500px",
                position: "absolute",
                top: 0,
                right: 0,
                border: "1px solid #DDDDDD"
            });
            $(wrapper).addClass('recent-query');

            var header = createElement('div', {
                innerText: "Recent Queryies"
            }, {
                background: "lightseagreen",
                padding: "5px",
                textAlign: "center",
                color: "white",
                fontSize: "16px",
                fontFamily: "verdana"
            });

            this.historyContainer = createElement("ul", {
                class: "msg_ul"
            }, {
                height: "100%",
                overflowY: "auto",
                marginTop: "5px"
            });
            wrapper.appendChild(header);
            wrapper.appendChild(this.historyContainer);
            document.body.appendChild(wrapper);
        },

        newMessage: function onNewMessage(msg) {
            if(typeof msg === "string") {
                msg = JSON.parse(msg);
            }

            msg.user = "http://placehold.it/30x30";
            msg.time = "now";

            var msgEl = createElement('li', {
                    class: "msg_li"
                }, {
                    height: "27px",
                    lineHeight: "27px",
                    width: "300px",
                    verticalAlign: "middle",
                    color: "#4B434D",
                    listStyleType: "none",
                    marginBottom: "5px",
                    display: "flex"
                }),
                queryEl = createElement('span', {
                    class: "msg_query",
                    innerText: msg.query
                }, {
                    fontSize: "12px",
                    maxWidth: "235px",
                    overflow: "hidden",
                    display: "inline-block",
                    textOverflow: "ellipsis",
                    whiteSpace: "nowrap",
                    flex: 10
                }),
                user = createElement("img", {
                    class: "msg_user",
                    src: msg.user
                }, {
                    flex: 1,
                    marginRight: "10px"
                }),
                time = createElement("span", {
                    class: "msg_time",
                    innerText: msg.time
                }, {
                    flex: 1,
                    fontSize: "12px"
                });

            msgEl.appendChild(user);
            msgEl.appendChild(queryEl);
            msgEl.appendChild(time);

            if(this.msgs.length) {
                this.historyContainer.insertBefore(msgEl, this.historyContainer.childNodes[0])
            } else {
                this.historyContainer.appendChild(msgEl);
            }

            this.msgs.push(msgEl);
        }
    }

    gdvf.hisCom.init();

});
