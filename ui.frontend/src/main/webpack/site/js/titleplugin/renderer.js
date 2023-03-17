import $ from 'jquery';

const Handlebars = require('handlebars');
const moment = require('moment');

export default class Renderer {
    constructor($el, data) {
        this.$el = $el;
        this.template = $el.data('templateId');
        this.registerHelper();
        this.render(data);
        this.handlebar = Handlebars;
    }

    registerHelper() {
        Handlebars.registerHelper('ifEquals', function(v1, v2, options) {
            if (v1 === v2) {
                return options.fn(this);
            }

            return options.inverse(this);
        });

        Handlebars.registerHelper('ifNotEquals', function(v1, v2, options) {
            if (v1 !== v2) {
                return options.fn(this);
            }

            return options.inverse(this);
        });

        // Loop using number
        Handlebars.registerHelper('times', function(n, block) {
            var accum = '';
            for(var i = 0; i < n; ++i) {
                block.data.index = i;
                block.data.first = i === 0;
                block.data.last = i === (n - 1);
                accum += block.fn(this);
            }
            return accum;
        });

        // Add
        Handlebars.registerHelper('inc', (number, options) => {
            if (typeof (number) === 'undefined' || number === null) {
                return null;
            }

            // Increment by inc parameter if it exists or just by one
            return number + (options.hash.inc || 1);
        });

        // Substract
        Handlebars.registerHelper('dec', (number, options) => {
            if (typeof (number) === 'undefined' || number === null) {
                return null;
            }

            // Increment by inc parameter if it exists or just by one
            return number - (options.hash.dec || 1);
        });

        Handlebars.registerHelper('switch', (value, options) => {
            this.switchValue = value;
            const html = options.fn(this); // Process the body of the switch block
            delete this.switchValue;
            return html;
        });

        Handlebars.registerHelper('eq', function () {
            const args = Array.prototype.slice.call(arguments, 0, -1);
            return args.every(function (expression) {
                return args[0] === expression;
            });
        });

        Handlebars.registerHelper('case', (...argus) => {
            // Convert "arguments" to a real array - stackoverflow.com/a/4775938
            const args = Array.prototype.slice.call(argus);
            const options = arguments.pop();
            const caseValues = args;
            if (caseValues[0] !== $.trim(this.switchValue)) {
                return '';
            }
            return options.fn(this);
        });
        // Divide
        Handlebars.registerHelper('divInt', (arg1, arg2) => parseInt(arg1 / arg2, 10));

        // less than or equal to
        Handlebars.registerHelper('ifLess', (a, b, options) => {
            return (a < b) ? options.fn(this) : options.inverse(this);
        });

        // Contains substring

        Handlebars.registerHelper('ifNotContainStr', function(v1, v2, options) {
            return v1.includes(v2) ? options.inverse(this) : options.fn(this);
        });
        Handlebars.registerHelper('ifContainStr', function(v1, v2, options) {
			if(v1){
				return v1.includes(v2) ? options.fn(this) : options.inverse(this);
			}else{
				return '';
			}
            
        });

        // great than or equal to
        Handlebars.registerHelper('ifGreat', function(v1, v2, options) {
            if (v1 > v2) {
                return options.fn(this);
            }

            return options.inverse(this);
        });

        Handlebars.registerHelper('objectLength', (json) => Object.keys(json).length);

        Handlebars.registerHelper('momentFormat', function(date, returnFormat) {
            let momDate = moment(date, 'YYYY-MM-DD HH:mm:ss');
            return momDate.format(returnFormat).trim();
        });

        Handlebars.registerHelper('splitBy', function(data, splitChara, pos) {
            if (data[0].length > 1) {
                return data[0].split(splitChara)[pos];
            }
            return data.split(splitChara)[pos];
        });

        Handlebars.registerHelper('removePTag', function(desc) {
            let pRemovedDesc = desc.replaceAll('<p>', '').replaceAll('</p>', '');
            return pRemovedDesc;
        });

        Handlebars.registerHelper('convertAnd', function(uri) {
            return uri.replaceAll('&', '%26');
        });
    }

    render(data) {
        const source = $(`#${this.template}`).html();
        const template = Handlebars.compile(source);
        const html = template(data);
        this.$el.html(html);
    }
}
