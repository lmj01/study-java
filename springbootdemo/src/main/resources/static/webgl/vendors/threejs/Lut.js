THREE.Lut = function ( colormap, numberofcolors ) {

            this.lut = [];
            this.setColorMap( colormap, numberofcolors );
            return this;

            };

            THREE.Lut.prototype = {

            constructor: THREE.Lut,

            lut: [], map: [], n: 256, minV: 0, maxV: 1,

            set: function ( value ) {

                if ( value instanceof THREE.Lut ) {

                    this.copy( value );

                }

                return this;

            },

            setMin: function ( min ) {

                this.minV = min;

                return this;

            },

            setMax: function ( max ) {

                this.maxV = max;

                return this;

            },

            setColorMap: function ( colormap, numberofcolors ) {

                this.map = [[ 0.0, 0xFF0000 ], [ 0.5, 0xFFFF00 ], [ 0.8, 0x00FF00 ], [ 1.5, 0xFF0000 ]];
                this.n = numberofcolors || 32;

                var step = 1.0 / this.n;

                this.lut.length = 0;
                for ( var i = 0; i <= 1; i += step ) {

                    for ( var j = 0; j < this.map.length - 1; j ++ ) {

                        if ( i >= this.map[ j ][ 0 ] && i < this.map[ j + 1 ][ 0 ] ) {

                            var min = this.map[ j ][ 0 ];
                            var max = this.map[ j + 1 ][ 0 ];

                            var minColor = new THREE.Color( this.map[ j ][ 1 ] );
                            var maxColor = new THREE.Color( this.map[ j + 1 ][ 1 ] );

                            var color = minColor.lerp( maxColor, ( i - min ) / ( max - min ) );

                            //this.lut.push( minColor );
                            this.lut.push(color);

                        }

                    }

                }

                return this;

            },

            copy: function ( lut ) {

                this.lut = lut.lut;
                this.map = lut.map;
                this.n = lut.n;
                this.minV = lut.minV;
                this.maxV = lut.maxV;

                return this;

            },

            getColor: function ( alpha ) {

                if ( alpha <= this.minV ) {

                    alpha = this.minV;

                } else if ( alpha >= this.maxV ) {

                    alpha = this.maxV;

                }
                
                alpha = ( alpha - this.minV ) / ( this.maxV - this.minV );
                
                var colorPosition = Math.round( alpha * this.n );                
                colorPosition == this.n ? colorPosition -= 1 : colorPosition;
                return this.lut[ colorPosition ];

            },
            
            createCanvas: function () {

                var canvas = document.createElement( 'canvas' );
                canvas.width = 1;
                canvas.height = this.n;

                this.updateCanvas( canvas );

                return canvas;

            },

            updateCanvas: function ( canvas ) {

                var ctx = canvas.getContext( '2d', { alpha: false } );

                var imageData = ctx.getImageData( 0, 0, 1, this.n );

                var data = imageData.data;

                var k = 0;

                var step = 1.0 / this.n;

                for ( var i = 1; i >= 0; i -= step ) {

                    for ( var j = this.map.length - 1; j >= 0; j -- ) {

                        if ( i < this.map[ j ][ 0 ] && i >= this.map[ j - 1 ][ 0 ] ) {

                            var min = this.map[ j - 1 ][ 0 ];
                            var max = this.map[ j ][ 0 ];

                            var minColor = new THREE.Color( this.map[ j - 1 ][ 1 ] );
                            var maxColor = new THREE.Color( this.map[ j ][ 1 ] );

                            //var color = minColor.lerp( maxColor, ( i - min ) / ( max - min ) );
                            var color = minColor;

                            data[ k * 4 ] = Math.round( color.r * 255 );
                            data[ k * 4 + 1 ] = Math.round( color.g * 255 );
                            data[ k * 4 + 2 ] = Math.round( color.b * 255 );
                            data[ k * 4 + 3 ] = 255;

                            k += 1;

                        }

                    }

                }

                ctx.putImageData( imageData, 0, 0 );

                return canvas;

            }
        };
